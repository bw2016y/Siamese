package org.daslab.sqlengine.expressions;

import org.daslab.sqlengine.plans.logical.unary.UnaryPlan;

import java.util.List;

public class UnresolvedAttribute extends Attribute {

    public UnresolvedAttribute(String name) {
        super(name);
    }

    @Override
    public List<Expression> children() {
        return null;
    }
}
