package org.apache.spark.daslab.sql.engine.expressions

//todo complete  expression / namedexpression

protected class AttributeEquals(val a: Attribute) {
  override def hashCode(): Int = a match {
    case ar: AttributeReference => ar.exprId.hashCode()
    case a => a.hashCode()
  }

  override def equals(other: Any): Boolean = (a, other.asInstanceOf[AttributeEquals].a) match {
    case (a1: AttributeReference, a2: AttributeReference) => a1.exprId == a2.exprId
    case (a1, a2) => a1 == a2
  }
}

object AttributeSet {
  /** Returns an empty [[AttributeSet]]. */
  val empty = apply(Iterable.empty)

  /** Constructs a new [[AttributeSet]] that contains a single [[Attribute]]. */
  def apply(a: Attribute): AttributeSet = new AttributeSet(Set(new AttributeEquals(a)))

  /** Constructs a new [[AttributeSet]] given a sequence of [[Expression Expressions]]. */
  def apply(baseSet: Iterable[Expression]): AttributeSet = {
    new AttributeSet(
      baseSet
        .flatMap(_.references)
        .map(new AttributeEquals(_)).toSet)
  }
}

/**
 * A Set designed to hold [[AttributeReference]] objects, that performs equality checking using
 * expression id instead of standard java equality.  Using expression id means that these
 * sets will correctly test for membership, even when the AttributeReferences in question differ
 * cosmetically (e.g., the names have different capitalizations).
 *
 * Note that we do not override equality for Attribute references as it is really weird when
 * `AttributeReference("a"...) == AttributeReference("b", ...)`. This tactic leads to broken tests,
 * and also makes doing transformations hard (we always try keep older trees instead of new ones
 * when the transformation was a no-op).
 */
class AttributeSet private (val baseSet: Set[AttributeEquals])
  extends Traversable[Attribute] with Serializable {

  override def hashCode: Int = baseSet.hashCode()

  /** Returns true if the members of this AttributeSet and other are the same. */
  override def equals(other: Any): Boolean = other match {
    case otherSet: AttributeSet =>
      otherSet.size == baseSet.size && baseSet.map(_.a).forall(otherSet.contains)
    case _ => false
  }

  /** Returns true if this set contains an Attribute with the same expression id as `elem` */
  def contains(elem: NamedExpression): Boolean =
    baseSet.contains(new AttributeEquals(elem.toAttribute))

  /** Returns a new [[AttributeSet]] that contains `elem` in addition to the current elements. */
  def +(elem: Attribute): AttributeSet =  // scalastyle:ignore
    new AttributeSet(baseSet + new AttributeEquals(elem))

  /** Returns a new [[AttributeSet]] that does not contain `elem`. */
  def -(elem: Attribute): AttributeSet =
    new AttributeSet(baseSet - new AttributeEquals(elem))

  /** Returns an iterator containing all of the attributes in the set. */
  def iterator: Iterator[Attribute] = baseSet.map(_.a).iterator

  /**
   * Returns true if the [[Attribute Attributes]] in this set are a subset of the Attributes in
   * `other`.
   */
  def subsetOf(other: AttributeSet): Boolean = baseSet.subsetOf(other.baseSet)

  /**
   * Returns a new [[AttributeSet]] that does not contain any of the [[Attribute Attributes]] found
   * in `other`.
   */
  def --(other: Traversable[NamedExpression]): AttributeSet =
    new AttributeSet(baseSet -- other.map(a => new AttributeEquals(a.toAttribute)))

  /**
   * Returns a new [[AttributeSet]] that contains all of the [[Attribute Attributes]] found
   * in `other`.
   */
  def ++(other: AttributeSet): AttributeSet = new AttributeSet(baseSet ++ other.baseSet)

  /**
   * Returns a new [[AttributeSet]] contain only the [[Attribute Attributes]] where `f` evaluates to
   * true.
   */
  override def filter(f: Attribute => Boolean): AttributeSet =
    new AttributeSet(baseSet.filter(ae => f(ae.a)))

  /**
   * Returns a new [[AttributeSet]] that only contains [[Attribute Attributes]] that are found in
   * `this` and `other`.
   */
  def intersect(other: AttributeSet): AttributeSet =
    new AttributeSet(baseSet.intersect(other.baseSet))

  override def foreach[U](f: (Attribute) => U): Unit = baseSet.map(_.a).foreach(f)

  // We must force toSeq to not be strict otherwise we end up with a [[Stream]] that captures all
  // sorts of things in its closure.
  override def toSeq: Seq[Attribute] = {
    // We need to keep a deterministic output order for `baseSet` because this affects a variable
    // order in generated code (e.g., `GenerateColumnAccessor`).
    // See SPARK-18394 for details.
    baseSet.map(_.a).toSeq.sortBy { a => (a.name, a.exprId.id) }
  }

  override def toString: String = "{" + baseSet.map(_.a).mkString(", ") + "}"

  override def isEmpty: Boolean = baseSet.isEmpty
}
