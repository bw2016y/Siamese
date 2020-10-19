package org.apache.spark.daslab.sql.engine.trees

import java.util.UUID

import org.apache.commons.lang3.ClassUtils
import org.apache.spark.daslab.sql.engine.IdentifierWithDatabase
import org.apache.spark.daslab.sql.engine.catalog.{BucketSpec, CatalogStorageFormat, CatalogTable, CatalogTableType, FunctionResource}
import org.apache.spark.daslab.sql.engine.errors._
import org.apache.spark.daslab.sql.engine.expressions.ExprId
import org.apache.spark.daslab.sql.engine.plans.JoinType
import org.apache.spark.daslab.sql.engine.plans.physical.{BroadcastMode, Partitioning}
import org.apache.spark.daslab.sql.types.{DataType, Metadata, StructField, StructType}
import org.apache.spark.daslab.sql.engine.ScalaReflection._
import org.apache.spark.daslab.sql.engine.plans.logical.{Confidence, ErrorRate}

import scala.collection.{Map, mutable}
import scala.reflect.ClassTag
import org.json4s.JsonAST._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

//TODO spark core
import org.apache.spark.util.Utils
import org.apache.spark.storage.StorageLevel



/**
 *    在TreeNode类中遍历树时被使用
 * @param i
 */
private class MutableInt(var i: Int)

/**
 *
 * @param line
 * @param startPosition
 */
case class Origin(line: Option[Int] = None,  startPosition: Option[Int] = None)

/**
 *  提供TreeNode的Origin上下文，当前正在解析的代码位置
 */
object CurrentOrigin {
  private val value = new ThreadLocal[Origin]() {
    override def initialValue: Origin = Origin()
  }

  def get: Origin = value.get()
  def set(o: Origin): Unit = value.set(o)

  def reset(): Unit = value.set(Origin())

  def setPosition(line: Int, start: Int): Unit = {
    value.set(
      value.get.copy(line = Some(line), startPosition = Some(start)))
  }

  def withOrigin[A](o: Origin)(f: => A): A = {
    set(o)
    val ret = try f finally { reset() }
    ret
  }
}

/**
 *
 * @param name tag名称
 * @tparam T   tag值的类型
 */
case class TreeNodeTag[T](name: String)

/**
  * 继承了Product,因此可以通过productElement函数或productIterator迭代器对Case Class参数信息进行索引和遍历
  * @tparam BaseType
  */
abstract class TreeNode[BaseType <: TreeNode[BaseType]] extends Product {

  //混入子类
  self: BaseType =>

  val origin: Origin = CurrentOrigin.get

  /**
   *  保存TreeNode节点的辅助信息，当该节点通过makeCopy拷贝或者通过transformUp/Down方法遍历时会被复制
   *  TODO　tags参考3.0.0实现
   */
  private val tags: mutable.Map[TreeNodeTag[_], Any] = mutable.Map.empty


  protected def copyTagsFrom(other: BaseType): Unit = {
    tags ++= other.tags
  }

  def setTagValue[T](tag: TreeNodeTag[T], value: T): Unit = {
    tags(tag) = value
  }

  def getTagValue[T](tag: TreeNodeTag[T]): Option[T] = {
    tags.get(tag).map(_.asInstanceOf[T])
  }

  def unsetTagValue[T](tag: TreeNodeTag[T]): Unit = {
    tags -= tag
  }

  /**
   *  返回该TreeNode节点的子节点序列
   *  子节点序列应当是不可变的
   * @return
   */
  def children: Seq[BaseType]

  lazy val containsChild: Set[TreeNode[_]] = children.toSet

  // hashcode
  private lazy val _hashCode: Int = scala.util.hashing.MurmurHash3.productHash(this)
  override def hashCode(): Int = _hashCode

  /**
   *  当该treeNode实例和other实例为同一实例时做了短路优化
   * @param other
   * @return
   */
  def fastEquals(other: TreeNode[_]): Boolean = {
       this == other || this.eq(other)
  }

  /**
   *  先序遍历Tree并返回第一个满足f条件的节点
   *
   * @param f
   * @return
   */
  def find(f: BaseType => Boolean): Option[BaseType] = if (f(this)) {
    Some(this)
  } else {
    children.foldLeft(Option.empty[BaseType]) { (l, r) => l.orElse(r.find(f)) }
  }

  /**
   *  先序遍历并应用规则
   * @param f 规则
   */
  def foreach(f: BaseType => Unit): Unit = {
    f(this)
    children.foreach(_.foreach(f))
  }

  /**
   * 后序遍历并应用规则
   * @param f
   */
  def foreachUp(f: BaseType => Unit): Unit = {
    children.foreach(_.foreachUp(f))
    f(this)
  }

  /**
   *　返回对树中节点应用规则后的的结果的序列
   *　
   * @param f
   */
  def map[A](f: BaseType => A): Seq[A] = {
    val ret = new collection.mutable.ArrayBuffer[A]()
    foreach(ret += f(_))
    ret
  }

  /**
   * 　同map，但是可以将返回的结果扁平化为一个序列
   * 　
   */
  def flatMap[A](f: BaseType => TraversableOnce[A]): Seq[A] = {
    val ret = new collection.mutable.ArrayBuffer[A]()
    foreach(ret ++= f(_))
    ret
  }

  /**
   *   返回对这个树所有节点应用偏函数的结果序列
   *
   */
  def collect[B](pf: PartialFunction[BaseType, B]): Seq[B] = {
    val ret = new collection.mutable.ArrayBuffer[B]()
    val lifted = pf.lift
    foreach(node => lifted(node).foreach(ret.+=))
    ret
  }

  /**
   * 返回所有叶节点
   */
  def collectLeaves(): Seq[BaseType] = {
    this.collect { case p if p.children.isEmpty => p }
  }

  /**
   *  先序遍历树，并返回第一个应用偏函数的结果
   *
   */
  def collectFirst[B](pf: PartialFunction[BaseType, B]): Option[B] = {
    val lifted = pf.lift
    lifted(this).orElse {
      children.foldLeft(Option.empty[B]) { (l, r) => l.orElse(r.collectFirst(pf)) }
    }
  }

  /**
   *  productIterator.map(f).toArray的快速版本
   */
  protected def mapProductIterator[B: ClassTag](f: Any => B): Array[B] = {
    val arr = Array.ofDim[B](productArity)
    var i = 0
    while (i < arr.length) {
      arr(i) = f(productElement(i))
      i += 1
    }
    arr
  }

  /**
   * 返回该节点带有新的子节点的一个拷贝
   * TODO: Validate somewhere (in debug mode?) that children are ordered correctly.
   */
  def withNewChildren(newChildren: Seq[BaseType]): BaseType = {
    assert(newChildren.size == children.size, "Incorrect number of children")
    var changed = false
    val remainingNewChildren = newChildren.toBuffer
    val remainingOldChildren = children.toBuffer
    def mapTreeNode(node: TreeNode[_]): TreeNode[_] = {
      val newChild = remainingNewChildren.remove(0)
      val oldChild = remainingOldChildren.remove(0)
      if (newChild fastEquals oldChild) {
        oldChild
      } else {
        changed = true
        newChild
      }
    }
    def mapChild(child: Any): Any = child match {
      case arg: TreeNode[_] if containsChild(arg) => mapTreeNode(arg)
      // CaseWhen Case or any tuple type
      case (left, right) => (mapChild(left), mapChild(right))
      case nonChild: AnyRef => nonChild
      case null => null
    }
    val newArgs = mapProductIterator {
      case s: StructType => s // Don't convert struct types to some other type of Seq[StructField]
      // Handle Seq[TreeNode] in TreeNode parameters.
      case s: Stream[_] =>
        // Stream is lazy so we need to force materialization
        s.map(mapChild).force
      case s: Seq[_] =>
        s.map(mapChild)
      case m: Map[_, _] =>
        // `mapValues` is lazy and we need to force it to materialize
        m.mapValues(mapChild).view.force
      case arg: TreeNode[_] if containsChild(arg) => mapTreeNode(arg)
      case Some(child) => Some(mapChild(child))
      case nonChild: AnyRef => nonChild
      case null => null
    }

    if (changed) makeCopy(newArgs) else this
  }

  /**
   *
   *  返回以先序遍历应用规则后的树
   *  如果规则未生效则不做改变
   *  可以用transformDown/transformUp来指定遍历的顺序
   *
   * @param rule 转换规则
   */
  def transform(rule: PartialFunction[BaseType, BaseType]): BaseType = {
    transformDown(rule)
  }

  /**
   *
   * pre-order
   *
   * @param rule
   */
  def transformDown(rule: PartialFunction[BaseType, BaseType]): BaseType = {
    val afterRule = CurrentOrigin.withOrigin(origin) {
      rule.applyOrElse(this, identity[BaseType])
    }

    // 如果未改变则直接返回旧值避免gc
    if (this fastEquals afterRule) {
      mapChildren(_.transformDown(rule))
    } else {
      // If the transform function replaces this node with a new one, carry over the tags.
      //TODO 2.4.5无此特性
      afterRule.copyTagsFrom(this)
      afterRule.mapChildren(_.transformDown(rule))
    }
  }

  /**
   *  post-order
   *
   *
   * @param rule
   */
  def transformUp(rule: PartialFunction[BaseType, BaseType]): BaseType = {
    val afterRuleOnChildren = mapChildren(_.transformUp(rule))
    val newNode = if (this fastEquals afterRuleOnChildren) {
      CurrentOrigin.withOrigin(origin) {
        rule.applyOrElse(this, identity[BaseType])
      }
    } else {
      CurrentOrigin.withOrigin(origin) {
        rule.applyOrElse(afterRuleOnChildren, identity[BaseType])
      }
    }
    // If the transform function replaces this node with a new one, carry over the tags.
    //TODO tags problem
    newNode.copyTagsFrom(this)
    newNode
  }

  /**
   *  将规则作用在该节点所有的子节点后返回该节点，默认不是深拷贝
   */
  def mapChildren(f: BaseType => BaseType): BaseType = {
    if (containsChild.nonEmpty) {
      mapChildren(f, forceCopy = false)
    } else {
      this
    }
  }

  /**
   * 同上
   * @param f  transform function
   * @param forceCopy 即使子节点没有变化也复制
   */
  private def mapChildren(f: BaseType => BaseType,
                           forceCopy: Boolean): BaseType = {
    var changed = false

    def mapChild(child: Any): Any = child match {
      case arg: TreeNode[_] if containsChild(arg) =>
        val newChild = f(arg.asInstanceOf[BaseType])
        if (forceCopy || !(newChild fastEquals arg)) {
          changed = true
          newChild
        } else {
          arg
        }
      case tuple @ (arg1: TreeNode[_], arg2: TreeNode[_]) =>
        val newChild1 = if (containsChild(arg1)) {
          f(arg1.asInstanceOf[BaseType])
        } else {
          arg1.asInstanceOf[BaseType]
        }

        val newChild2 = if (containsChild(arg2)) {
          f(arg2.asInstanceOf[BaseType])
        } else {
          arg2.asInstanceOf[BaseType]
        }

        if (forceCopy || !(newChild1 fastEquals arg1) || !(newChild2 fastEquals arg2)) {
          changed = true
          (newChild1, newChild2)
        } else {
          tuple
        }
      case other => other
    }

    val newArgs = mapProductIterator {
      case arg: TreeNode[_] if containsChild(arg) =>
        val newChild = f(arg.asInstanceOf[BaseType])
        if (forceCopy || !(newChild fastEquals arg)) {
          changed = true
          newChild
        } else {
          arg
        }
      case Some(arg: TreeNode[_]) if containsChild(arg) =>
        val newChild = f(arg.asInstanceOf[BaseType])
        if (forceCopy || !(newChild fastEquals arg)) {
          changed = true
          Some(newChild)
        } else {
          Some(arg)
        }
      case m: Map[_, _] => m.mapValues {
        case arg: TreeNode[_] if containsChild(arg) =>
          val newChild = f(arg.asInstanceOf[BaseType])
          if (forceCopy || !(newChild fastEquals arg)) {
            changed = true
            newChild
          } else {
            arg
          }
        case other => other
      }.view.force // `mapValues` is lazy and we need to force it to materialize
      case d: DataType => d // Avoid unpacking Structs
      case args: Stream[_] => args.map(mapChild).force // Force materialization on stream
      case args: Iterable[_] => args.map(mapChild)
      case nonChild: AnyRef => nonChild
      case null => null
    }
    if (forceCopy || changed) makeCopy(newArgs, forceCopy) else this
  }

  /**
   *  传给构造函数中应当被拷贝的参数
   *  会自动通过makeCopy函数追加到转换的参数中
   * @return
   */
  protected def otherCopyArgs: Seq[AnyRef] = Nil

  /**
   *  在转换后构造树节点的一个拷贝
   *  如果子类的构造参数中有未在ProductIterator中遍历的则需要重写
   *
   * @param newArgs 新实例的参数
   */
  def makeCopy(newArgs: Array[AnyRef]): BaseType = makeCopy(newArgs, allowEmptyArgs = false)

  /**
   * Creates a copy of this type of tree node after a transformation.
   * Must be overridden by child classes that have constructor arguments
   * that are not present in the productIterator.
   * @param newArgs 新参数
   * @param allowEmptyArgs 是否允许参数列表为空
   */
  private def makeCopy(
                        newArgs: Array[AnyRef],
                        allowEmptyArgs: Boolean): BaseType = attachTree(this, "makeCopy") {
    val allCtors = getClass.getConstructors
    if (newArgs.isEmpty && allCtors.isEmpty) {
      // This is a singleton object which doesn't have any constructor. Just return `this` as we
      // can't copy it.
      return this
    }

    // 跳过无参的构造函数，或者允许无参
    val ctors = allCtors.filter(allowEmptyArgs || _.getParameterTypes.size != 0)
    if (ctors.isEmpty) {
      sys.error(s"No valid constructor for $nodeName")
    }
    val allArgs: Array[AnyRef] = if (otherCopyArgs.isEmpty) {
      newArgs
    } else {
      newArgs ++ otherCopyArgs
    }
    val defaultCtor = ctors.find { ctor =>
      if (ctor.getParameterTypes.length != allArgs.length) {
        false
      } else if (allArgs.contains(null)) {
        // if there is a `null`, we can't figure out the class, therefore we should just fallback
        // to older heuristic
        false
      } else {
        val argsArray: Array[Class[_]] = allArgs.map(_.getClass)
        ClassUtils.isAssignable(argsArray, ctor.getParameterTypes, true /* autoboxing */)
      }
    }.getOrElse(ctors.maxBy(_.getParameterTypes.length)) // 否则选择参数列表最长的构造函数

    try {
      CurrentOrigin.withOrigin(origin) {
        val res = defaultCtor.newInstance(allArgs.toArray: _*).asInstanceOf[BaseType]
        //TODO tags
        res.copyTagsFrom(this)
        res
      }
    } catch {
      case e: java.lang.IllegalArgumentException =>
        throw new TreeNodeException(
          this,
          s"""
             |Failed to copy node.
             |Is otherCopyArgs specified correctly for $nodeName.
             |Exception message: ${e.getMessage}
             |ctor: $defaultCtor?
             |types: ${newArgs.map(_.getClass).mkString(", ")}
             |args: ${newArgs.mkString(", ")}
           """.stripMargin)
    }
  }

  /**
   * 深拷贝
   * @return
   */
  override def clone(): BaseType = {
    mapChildren(_.clone(), forceCopy = true)
  }

  /**
   *
   * @return 返回该TreeNode的类型名称，默认是classname
   *         去除了物理计划的后缀Exec
   */
  def nodeName: String = getClass.getSimpleName.replaceAll("Exec$", "")

  /**
   * 返回所有参数列表的迭代器，默认使用productIterator
   */
  protected def stringArgs: Iterator[Any] = productIterator

  // allChildren=children++innerChildren
  private lazy val allChildren: Set[TreeNode[_]] = (children ++ innerChildren).toSet[TreeNode[_]]

  /**
   * 返回代表该节点参数（除去所有children信息）的string信息
   * @return
   */
  def argString: String = stringArgs.flatMap {
    case tn: TreeNode[_] if allChildren.contains(tn) => Nil
    case Some(tn: TreeNode[_]) if allChildren.contains(tn) => Nil
    case Some(tn: TreeNode[_]) => tn.simpleString :: Nil
    case tn: TreeNode[_] => tn.simpleString :: Nil
    case seq: Seq[Any] if seq.toSet.subsetOf(allChildren.asInstanceOf[Set[Any]]) => Nil
    case iter: Iterable[_] if iter.isEmpty => Nil
    case seq: Seq[_] => Utils.truncatedString(seq, "[", ", ", "]") :: Nil
    case set: Set[_] => Utils.truncatedString(set.toSeq, "{", ", ", "}") :: Nil
    case array: Array[_] if array.isEmpty => Nil
    case array: Array[_] => Utils.truncatedString(array, "[", ", ", "]") :: Nil
    case null => Nil
    case None => Nil
    case Some(null) => Nil
    case Some(any) => any :: Nil
    case table: CatalogTable =>
      table.storage.serde match {
        case Some(serde) => table.identifier :: serde :: Nil
        case _ => table.identifier :: Nil
      }
    case error: ErrorRate => (error.simpleString+":"+error.errorRate)::Nil
    case confidence :Confidence => (confidence.simpleString+":"+confidence.confidence)::Nil
    case other => other :: Nil
  }.mkString(", ")




  /**
   *
   * @return 返回该节点的描述信息
   */
  def simpleString: String = s"$nodeName $argString".trim

  /**
   * ONE line description of this node containing the node identifier.
   * @return
   * TODO 2.4.5 not compatible
   */
 // def simpleStringWithNodeId(): String

  /**
   *
   *  @return 返回更加详细的节点信息
   *
   */
  def verboseString: String

  /**
   *
   * @return 返回更加详细的节点信息并附带后缀
   */
  def verboseStringWithSuffix: String = verboseString

  override def toString: String = treeString

  /**
   *
   * @return 返回该节点的树状信息
   */
  def treeString: String = treeString(verbose = true)

  def treeString(verbose: Boolean, addSuffix: Boolean = false): String = {
    generateTreeString(0, Nil, new StringBuilder, verbose = verbose, addSuffix = addSuffix).toString
  }


  /**
   *  返回可以表示节点与树关系的字符串，其中每个操作符都指定id,可以使用TreeNode.apply方法访问对应的子树
   *
   *  这些id是由depth-first的遍历方式指定的（优先遍历inner Children然后是children）
   * @return
   */
  def numberedTreeString: String =
    treeString.split("\n").zipWithIndex.map { case (line, i) => f"$i%02d $line" }.mkString("\n")

  /**
   *  返回指定id的TreeNode，主要用于debugging
   *
   *  这里不反悔BaseType的原因是logical plan的plan node的innerChildren有可能返回physical plan
   * @param number
   * @return
   */
  def apply(number: Int): TreeNode[_] = getNodeNumbered(new MutableInt(number)).orNull


  /**
   *  返回指定id的TreeNode，主要用于debugging
   *
   *   apply的变体，如果类型匹配则返回BaseType
   * @param number
   * @return
   */
  def p(number: Int): BaseType = apply(number).asInstanceOf[BaseType]

  private def getNodeNumbered(number: MutableInt): Option[TreeNode[_]] = {
    if (number.i < 0) {
      None
    } else if (number.i == 0) {
      Some(this)
    } else {
      number.i -= 1
      // Note that this traversal order must be the same as numberedTreeString.
      innerChildren.map(_.getNodeNumbered(number)).find(_ != None).getOrElse {
        children.map(_.getNodeNumbered(number)).find(_ != None).flatten
      }
    }
  }

  /**
   *  该节点的inner nested tree中的所有节点
   *  可以用来展示子查询
   * @return
   */
   protected def innerChildren: Seq[TreeNode[_]] = Seq.empty

  /**
   *  将该节点和其子节点的表示信息追加到StringBuilder中
   *
   *  lastChildren中的i-th个元素表示当前节点在i+1层的祖先是否为它父节点的最后一个子节点
   *  根节点的depth为0，lastChildren应该为空
   *
   *  遍历方式需要和getNodeNumbered相同
   *
   */
  def generateTreeString(
                          depth: Int,
                          lastChildren: Seq[Boolean],
                          builder: StringBuilder,
                          verbose: Boolean,
                          prefix: String = "",
                          addSuffix: Boolean = false): StringBuilder = {

    if (depth > 0) {

      lastChildren.init.foreach( (isLast)=>{builder.append(if(isLast) "      " else ":     " );} )
      builder.append("|  "+"\n")

      lastChildren.init.foreach { isLast =>
        builder.append(if (isLast) "      " else ":     ")
      }
      builder.append(if (lastChildren.last) "+- " else "|- ")
    }

    val str = if (verbose) {
      if (addSuffix) verboseStringWithSuffix else verboseString
    } else {
      simpleString
    }
    builder.append(prefix)
    builder.append(str)
    builder.append("\n")

    if (innerChildren.nonEmpty) {
      innerChildren.init.foreach(_.generateTreeString(
        depth + 2, lastChildren :+ children.isEmpty :+ false, builder, verbose,
       "innerChildren: ", addSuffix = addSuffix))
      innerChildren.last.generateTreeString(
        depth + 2, lastChildren :+ children.isEmpty :+ true, builder, verbose,
        "innerChildren: ",addSuffix = addSuffix)
    }

    if (children.nonEmpty) {
      children.init.foreach(_.generateTreeString(
        depth + 1, lastChildren :+ false, builder, verbose,"child: ", addSuffix))
      children.last.generateTreeString(
        depth + 1, lastChildren :+ true, builder, verbose, "child: ", addSuffix)
    }

    builder
  }


  /**
   *  返回该节点和其子节点的Scala code表示
   *  用于在debugging阶段使用
   * @return
   */
  def asCode: String = {
    val args = productIterator.map {
      case tn: TreeNode[_] => tn.asCode
      case s: String => "\"" + s + "\""
      case other => other.toString
    }
    s"$nodeName(${args.mkString(",")})"
  }

  def toJSON: String = compact(render(jsonValue))

  def prettyJson: String = pretty(render(jsonValue))

  private def jsonValue: JValue = {
    val jsonValues = scala.collection.mutable.ArrayBuffer.empty[JValue]

    def collectJsonValue(tn: BaseType): Unit = {
      val jsonFields = ("class" -> JString(tn.getClass.getName)) ::
        ("num-children" -> JInt(tn.children.length)) :: tn.jsonFields
      jsonValues += JObject(jsonFields)
      tn.children.foreach(collectJsonValue)
    }

    collectJsonValue(this)
    jsonValues
  }

  protected def jsonFields: List[JField] = {
    val fieldNames = getConstructorParameterNames(getClass)
    val fieldValues = productIterator.toSeq ++ otherCopyArgs
    assert(fieldNames.length == fieldValues.length, s"${getClass.getSimpleName} fields: " +
      fieldNames.mkString(", ") + s", values: " + fieldValues.map(_.toString).mkString(", "))

    fieldNames.zip(fieldValues).map {
      // If the field value is a child, then use an int to encode it, represents the index of
      // this child in all children.
      case (name, value: TreeNode[_]) if containsChild(value) =>
        name -> JInt(children.indexOf(value))
      case (name, value: Seq[BaseType]) if value.forall(containsChild) =>
        name -> JArray(
          value.map(v => JInt(children.indexOf(v.asInstanceOf[TreeNode[_]]))).toList
        )
      case (name, value) => name -> parseToJson(value)
    }.toList
  }

  private def parseToJson(obj: Any): JValue = obj match {
    case b: Boolean => JBool(b)
    case b: Byte => JInt(b.toInt)
    case s: Short => JInt(s.toInt)
    case i: Int => JInt(i)
    case l: Long => JInt(l)
    case f: Float => JDouble(f)
    case d: Double => JDouble(d)
    case b: BigInt => JInt(b)
    case null => JNull
    case s: String => JString(s)
    case u: UUID => JString(u.toString)
    case dt: DataType => dt.jsonValue
    // SPARK-17356: In usage of mllib, Metadata may store a huge vector of data, transforming
    // it to JSON may trigger OutOfMemoryError.
    case m: Metadata => Metadata.empty.jsonValue
    case clazz: Class[_] => JString(clazz.getName)
    case s: StorageLevel =>
      ("useDisk" -> s.useDisk) ~ ("useMemory" -> s.useMemory) ~ ("useOffHeap" -> s.useOffHeap) ~
        ("deserialized" -> s.deserialized) ~ ("replication" -> s.replication)
    case n: TreeNode[_] => n.jsonValue
    case o: Option[_] => o.map(parseToJson)
    // Recursive scan Seq[TreeNode], Seq[Partitioning], Seq[DataType]
    case t: Seq[_] if t.forall(_.isInstanceOf[TreeNode[_]]) ||
      t.forall(_.isInstanceOf[Partitioning]) || t.forall(_.isInstanceOf[DataType]) =>
      JArray(t.map(parseToJson).toList)
    case t: Seq[_] if t.length > 0 && t.head.isInstanceOf[String] =>
      JString(Utils.truncatedString(t, "[", ", ", "]"))
    case t: Seq[_] => JNull
    case m: Map[_, _] => JNull
    // if it's a scala object, we can simply keep the full class path.
    // TODO: currently if the class name ends with "$", we think it's a scala object, there is
    // probably a better way to check it.
    case obj if obj.getClass.getName.endsWith("$") => "object" -> obj.getClass.getName
    case p: Product if shouldConvertToJson(p) =>
      try {
        val fieldNames = getConstructorParameterNames(p.getClass)
        val fieldValues = p.productIterator.toSeq
        assert(fieldNames.length == fieldValues.length)
        ("product-class" -> JString(p.getClass.getName)) :: fieldNames.zip(fieldValues).map {
          case (name, value) => name -> parseToJson(value)
        }.toList
      } catch {
        case _: RuntimeException => null
      }
    case _ => JNull
  }

  private def shouldConvertToJson(product: Product): Boolean = product match {
    case exprId: ExprId => true
    case field: StructField => true
    case id: IdentifierWithDatabase => true
    case join: JoinType => true
    case spec: BucketSpec => true
    case catalog: CatalogTable => true
    case partition: Partitioning => true
    case resource: FunctionResource => true
    case broadcast: BroadcastMode => true
    case table: CatalogTableType => true
    case storage: CatalogStorageFormat => true
    case _ => false
  }

}
