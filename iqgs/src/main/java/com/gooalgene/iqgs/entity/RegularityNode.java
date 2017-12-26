package com.gooalgene.iqgs.entity;

public class RegularityNode {
    /**
     * 原始基因
     */
    private String source;

    /**
     * 指向基因
     */
    private String target;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
