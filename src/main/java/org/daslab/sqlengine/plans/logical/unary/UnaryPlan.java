package org.daslab.sqlengine.plans.logical.unary;

import org.daslab.sqlengine.plans.logical.LogicalPlan;

import java.util.ArrayList;
import java.util.List;

public abstract class UnaryPlan extends LogicalPlan {

    private LogicalPlan child;

    public UnaryPlan(LogicalPlan child) {
        this.child = child;
    }

    @Override
    public List<LogicalPlan> children() {
        ArrayList<LogicalPlan> children = new ArrayList<>();
        children.add(child);
        return children;
    }
}
