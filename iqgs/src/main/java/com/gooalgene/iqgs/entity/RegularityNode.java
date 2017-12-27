package com.gooalgene.iqgs.entity;

/**
 * 调控网络nodes，由GeneID、Hierarchy组成
 * @author crabime
 */
public class RegularityNode {
    /**
     * 基因ID
     */
    private String geneId;

    /**
     * node所处的层级
     */
    private int hierarchy;

    public RegularityNode(String geneId, int hierarchy) {
        this.geneId = geneId;
        this.hierarchy = hierarchy;
    }

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public int getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(int hierarchy) {
        this.hierarchy = hierarchy;
    }
}
