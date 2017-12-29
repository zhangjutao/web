package com.gooalgene.iqgs.entity;

/**
 * 调控网络nodes，由GeneID、Hierarchy组成
 * @author crabime
 */
public class RegularityNode {
    /**
     * 基因ID
     */
    private String geneID;

    /**
     * node所处的层级
     */
    private int hierarchy;

    public RegularityNode(String geneId, int hierarchy) {
        this.geneID = geneId;
        this.hierarchy = hierarchy;
    }

    public String getGeneID() {
        return geneID;
    }

    public void setGeneID(String geneID) {
        this.geneID = geneID;
    }

    public int getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(int hierarchy) {
        this.hierarchy = hierarchy;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof RegularityNode)){
            return false;
        }
        RegularityNode node = (RegularityNode) obj;
        return node.getGeneID().equals(this.geneID) && (node.getHierarchy() == this.hierarchy);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + geneID.hashCode();
        result = 31 * result + hierarchy;
        return result;
    }
}
