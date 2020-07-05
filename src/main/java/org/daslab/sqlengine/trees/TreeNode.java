package org.daslab.sqlengine.trees;

import scala.Option;
import scala.collection.Seq;
import scala.xml.dtd.impl.Base;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class TreeNode<BaseType extends TreeNode<BaseType>> {

    /**
     * tags 保存TreeNode的辅助信息 ， 当TreeNode被拷贝，或者transformed （transformUp/transformDown）时会被传递
     */
    protected final Map<TreeNodeTag<?>,Object> tags=new HashMap<>();

    protected void copyTagsFrom(BaseType other){
        tags.putAll(other.tags);
    }
    public <T> void setTagValue(TreeNodeTag<T> tag,T value){
        tags.put(tag,value);
    }
    public <T> Optional<T> getTagValue(TreeNodeTag<T> tag){
        return (Optional<T>) Optional.ofNullable(tags.get(tag));
    }
    public <T> void unsetTagValue(TreeNodeTag<T> tag){
        tags.remove(tag);
    }


    public abstract List<BaseType> children();

    /**
     *先序遍历树，对每个节点应用action
     * @param action
     */
    public void foreach(Consumer<BaseType> action){
        action.accept((BaseType) this);
        children().forEach(child -> child.foreach(action));
    }

    /**
     *后序遍历树，对每个节点应用action
     * @param action
     */
    public void foreachUp(Consumer<BaseType> action){
        children().forEach(child -> child.foreachUp(action));
        action.accept((BaseType)this);
    }

    /**
     *  先序遍历应用规则并返回结果列表
     * @param f 应用的规则
     * @param <T>   返回容器数据类型
     * @return
     */
    public <T> List<T> map(Function<BaseType,T> f){
        List<T> res=new ArrayList<>();
        foreach((node)->{res.add(f.apply(node));});
        return res;
    }


    /**
     * 用predicate模拟scala中的partialFunction，并对每个满足条件的节点应用action，将结果放入List返回
     */
    public <R> List<R> collect(Predicate<BaseType> predicate, Function<BaseType, R> action){
        ArrayList<R> ret = new ArrayList<>();
        foreach((node)->{
            if(predicate.test(node)){
                ret.add(action.apply(node));
            }
        });
        return ret;
    }

    /**
     *  flatMap 将可能返回集合的function的结果扁平化放到容器中
     * @param f
     * @param <T>
     * @return
     */
    public <T> List<T> flatMap(Function<BaseType,Iterable<T>> f){
        List<T> res=new ArrayList<>();
        foreach((node)->{
            for(T t:f.apply(node)){
                res.add(t);
            }
        });
        return res;
    }


    /**
     * 收集所有叶子节点
     */
    public List<BaseType> collectLeaves(){
        return this.collect((BaseType node)->{return node.children().isEmpty();},(node)->{return (BaseType) node;});

        /*List<BaseType> ret = collect((BaseType node) -> {
            if (node.children().isEmpty()) {
                return node;
            } else {
                return null;
            }
        });
        ret.forEach((node) -> {
            if (node == null) {
                ret.remove(null);
            }
        });
        return ret;*/
    }

    /**
     *  找到这个Tree中符合要求并经过转换的第一个结果，有可能没有
     *  具体的遍历顺序取决于children()的遍历规则
     *  todo add traversal method
     * @param predicate
     * @param f
     * @param <T>
     * @return
     */
    public <T> Optional<T> collectFirst(Predicate<BaseType> predicate,Function<BaseType,T> f){
        return this.children().stream().filter(predicate).map(f).findFirst();
    }

    /**
     * 对tree应用规则，如果规则无法生效，则保留原始状态
     * 用户不需要指定具体的遍历规则，否则应该使用transformDown/transformUp
     * @param rule
     * @return
     */
    public BaseType transform(Predicate<BaseType> predicate, Function<BaseType,BaseType> rule){
        transformDown(predicate, rule);
    }


    /**
     * TODO：前序遍历，对每个节点应用规则
     * TODO : test this
     * @return
     */
    public BaseType transformDown(Predicate<BaseType> predicate, Function<BaseType,BaseType> rule) {

        BaseType afterRule= predicate.test((BaseType) this)?rule.apply((BaseType) this):(BaseType)this;


        if(this.fastEquals(afterRule)){
            this.mapChildren();
        }
        else {
            afterRule.copyTagsFrom((BaseType) this);
            afterRule.mapChildren();
        }
        return afterRule;
    }

    /**
     * TODO: 后序遍历，对每个节点应用规则
     * TODO: TEST THIS
     * @return
     */
    public BaseType transformUp(Predicate<BaseType> predicate, Function<BaseType,BaseType> rule) {
        BaseType afterRuleOnChildren = mapChildren();
        BaseType newNode=null;
        if(this.fastEquals(afterRuleOnChildren)){
            if(predicate.test((BaseType) this)){
                newNode=rule.apply((BaseType) this);
            }
            else{
                newNode=(BaseType)this;
            }
        }
        else {
            if(predicate.test(afterRuleOnChildren)){
                newNode=rule.apply(afterRuleOnChildren);
            }
            else {
                newNode=afterRuleOnChildren;
            }
        }
        newNode.copyTagsFrom((BaseType) this);
        return newNode;
    }

    /**
     * TODO：对所有children应用函数
     *  优化 仿照HashMap增加children视图，节省开销
     * @return
     */
    public BaseType mapChildren(Function<BaseType,BaseType> function) {
        return this.mapChildren(function,false);
    }

    /**
     * TODO   未实现   语言学家翻译不动了...
     * @param function
     * @param forceCopy
     * @return
     */
    public BaseType mapChildren(Function<BaseType,BaseType> function,boolean forceCopy){
        boolean changed=false;
        return null;
    }


    @Override
    public int hashCode() {
        return this.tags.hashCode();
    }

    /**
     * 在默认类型相同情况下的faster版本的equals
     * @param other
     * @return
     */
    public boolean fastEquals(TreeNode<?> other){
        return this==other || this.equals(other);
    }


    @Override
    public boolean equals(Object obj) {

        if(this==obj)
            return true;
        if(obj==null)
            return false;
        if(this.getClass()!=obj.getClass())
            return false;


        TreeNode other=(TreeNode)obj;

        return this.tags.equals(other.tags) && this.children().equals(other.children());

    }
}

/**
 *  TreeNode的tag信息，定义了name和type
 * @param <T>  value的类型
 */
class TreeNodeTag<T>{

    public final Class<T> type;
    public final String name;


    public TreeNodeTag(String name,Class<T> type){
        this.type=type;
        this.name=name;
    }




    @Override
    public int hashCode() {
        int res= Objects.hash(name,type);
        return res;
    }

    @Override
    public boolean equals(Object obj) {

        if(this==obj)
            return true;
        if(obj==null)
            return false;
        if(this.getClass()!=obj.getClass())
            return false;


        TreeNodeTag other=(TreeNodeTag)obj;

        return this.name.equals(other.name) && this.type.equals(other.type);
    }
}