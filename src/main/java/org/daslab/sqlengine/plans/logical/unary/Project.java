package org.daslab.sqlengine.plans.logical.unary;

import org.daslab.sqlengine.expressions.UnresolvedAttribute;
import org.daslab.sqlengine.plans.logical.LogicalPlan;

import java.util.List;

public class Project extends UnaryPlan {

    private List<UnresolvedAttribute> projectList;

    public Project(List<UnresolvedAttribute> projectList, LogicalPlan child) {
        super(child);
        this.projectList = projectList;
    }


}
