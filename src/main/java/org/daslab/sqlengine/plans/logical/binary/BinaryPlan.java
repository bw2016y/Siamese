package org.daslab.sqlengine.plans.logical.binary;

import org.daslab.sqlengine.plans.logical.LogicalPlan;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class BinaryPlan extends LogicalPlan {

    private LogicalPlan left;
    private LogicalPlan right;

    public BinaryPlan(LogicalPlan left, LogicalPlan right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public List<LogicalPlan> children() {
        ArrayList<LogicalPlan> children = new ArrayList<>();
        children.add(left);
        children.add(right);
        return children;
    }
}
