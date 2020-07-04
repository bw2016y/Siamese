package org.daslab.sqlengine.expressions;

import java.util.List;

public abstract class Attribute extends Expression {

    private String name;

    public Attribute(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
