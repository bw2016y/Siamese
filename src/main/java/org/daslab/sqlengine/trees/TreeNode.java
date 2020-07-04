package org.daslab.sqlengine.trees;

import scala.Option;
import scala.collection.Seq;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class TreeNode<BaseType extends TreeNode<BaseType>> {


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
     *先序遍历树，对每个节点应用action
     * @param action
     */
    public void foreachUp(Consumer<BaseType> action){
        children().forEach(child -> child.foreach(action));
        action.accept((BaseType)this);
    }

    /**
     * 对每个节点应用action，将结果放入List返回
     */
    public <R> List<R> collect(Function<BaseType, R> action){
        ArrayList<R> ret = new ArrayList<>();
        foreach(node -> ret.add(action.apply(node)));
        return ret;
    }

    /**
     * 收集所有叶子节点
     */
    public List<BaseType> collectLeaves(){
        List<BaseType> ret = collect((BaseType node) -> {
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
        return ret;
    }

    /**
     * TODO：前序遍历，对每个节点应用规则
     * @return
     */
    public BaseType transformDown() {
        return null;
    }

    /**
     * TODO: 后序遍历，对每个节点应用规则
     * @return
     */
    public BaseType transformUp() {
        return null;
    }

    /**
     * TODO：对所有children应用函数
     * @return
     */
    public BaseType mapChildren() {
        return null;
    }


}
