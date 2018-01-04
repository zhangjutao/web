package com.gooalgene.iqgs.entity;

/**
 * 单个基因FPKM实体
 */
public class GeneFPKM extends Tissue {
    /**
     * 主键字段
     */
    private int id;

    /**
     * 基因ID
     */
    private String geneId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }
}
