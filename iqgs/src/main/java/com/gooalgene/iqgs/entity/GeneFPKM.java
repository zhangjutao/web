package com.gooalgene.iqgs.entity;

import com.google.common.base.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GeneFPKM geneFPKM = (GeneFPKM) o;
        return id == geneFPKM.id &&
                Objects.equal(geneId, geneFPKM.geneId);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), id, geneId);
    }
}
