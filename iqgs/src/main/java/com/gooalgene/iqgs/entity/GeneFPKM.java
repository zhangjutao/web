package com.gooalgene.iqgs.entity;

import javax.persistence.Column;

/**
 * 基因FPKM表(含外键关联study表)
 * @author crabime
 */
public class GeneFPKM {
    /**
     * 主键字段
     */
    private int id;

    /**
     * 外键关联字段，关联samplerun表中的id字段
     */
    private int sampleRunId;

    /**
     * 基因ID
     */
    private String geneId;

    /**
     * 该基因的FPKM值
     */
    private double fpkmValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSampleRunId() {
        return sampleRunId;
    }

    public void setSampleRunId(int sampleRunId) {
        this.sampleRunId = sampleRunId;
    }

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public double getFpkmValue() {
        return fpkmValue;
    }

    public void setFpkmValue(double fpkmValue) {
        this.fpkmValue = fpkmValue;
    }
}
