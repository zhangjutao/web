package com.gooalgene.dna.entity.result;

import java.util.Map;

public class GroupCondition {
    private String name;
    private Map<String, Object> condition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getCondition() {
        return condition;
    }

    public void setCondition(Map<String, Object> condition) {
        this.condition = condition;
    }
}
