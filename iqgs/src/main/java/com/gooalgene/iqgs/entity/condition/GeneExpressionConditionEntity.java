package com.gooalgene.iqgs.entity.condition;

import com.gooalgene.iqgs.entity.Tissue;

/**
 * 基因表达量查询单个组织实体对象
 * @author crabime
 * @since 12/13/2017
 */
public class GeneExpressionConditionEntity {
    /**
     * 某一组织下所选二级组织集合
     */
    private Tissue tissue;
    /**
     * 某一组织下FPKM最小值
     */
    private Double begin;
    /**
     * 某一组织下FPKM最大值
     */
    private Double end;

    public Tissue getTissue() {
        return tissue;
    }

    public void setTissue(Tissue tissue) {
        this.tissue = tissue;
    }

    public Double getBegin() {
        return begin;
    }

    public void setBegin(Double begin) {
        this.begin = begin;
    }

    public Double getEnd() {
        return end;
    }

    public void setEnd(Double end) {
        this.end = end;
    }
}
