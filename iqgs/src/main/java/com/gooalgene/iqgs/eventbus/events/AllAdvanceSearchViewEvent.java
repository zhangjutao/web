package com.gooalgene.iqgs.eventbus.events;

import com.gooalgene.dna.entity.DNAGenStructure;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.Tissue;
import com.gooalgene.iqgs.entity.condition.GeneExpressionCondition;
import com.gooalgene.iqgs.entity.condition.GeneExpressionConditionEntity;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 获取所有高级搜索结果事件
 */
public class AllAdvanceSearchViewEvent {
    private List<GeneExpressionConditionEntity> condition;
    private List<String> selectSnp;
    private List<String> selectIndel;
    private List<Integer> firstHierarchyQtlId;
    private List<Integer> selectQTL;
    private DNAGenBaseInfo baseInfo;
    private DNAGenStructure structure;

    public AllAdvanceSearchViewEvent(List<GeneExpressionConditionEntity> condition,
                                     List<String> selectSnp, List<String> selectIndel,
                                     List<Integer> firstHierarchyQtlId, List<Integer> selectQTL,
                                     DNAGenBaseInfo baseInfo, DNAGenStructure structure) {
        this.condition = condition;
        this.selectSnp = selectSnp;
        this.selectIndel = selectIndel;
        this.firstHierarchyQtlId = firstHierarchyQtlId;
        this.selectQTL = selectQTL;
        this.baseInfo = baseInfo;
        this.structure = structure;
    }

    public List<GeneExpressionConditionEntity> getCondition() {
        return condition;
    }

    public void setCondition(List<GeneExpressionConditionEntity> condition) {
        this.condition = condition;
    }

    public List<String> getSelectSnp() {
        return selectSnp;
    }

    public void setSelectSnp(List<String> selectSnp) {
        this.selectSnp = selectSnp;
    }

    public List<String> getSelectIndel() {
        return selectIndel;
    }

    public void setSelectIndel(List<String> selectIndel) {
        this.selectIndel = selectIndel;
    }

    public List<Integer> getFirstHierarchyQtlId() {
        return firstHierarchyQtlId;
    }

    public void setFirstHierarchyQtlId(List<Integer> firstHierarchyQtlId) {
        this.firstHierarchyQtlId = firstHierarchyQtlId;
    }

    public List<Integer> getSelectQTL() {
        return selectQTL;
    }

    public void setSelectQTL(List<Integer> selectQTL) {
        this.selectQTL = selectQTL;
    }

    public DNAGenBaseInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(DNAGenBaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    public DNAGenStructure getStructure() {
        return structure;
    }

    public void setStructure(DNAGenStructure structure) {
        this.structure = structure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllAdvanceSearchViewEvent event = (AllAdvanceSearchViewEvent) o;
        return Objects.equal(condition, event.condition) &&
                Objects.equal(selectSnp, event.selectSnp) &&
                Objects.equal(selectIndel, event.selectIndel) &&
                Objects.equal(firstHierarchyQtlId, event.firstHierarchyQtlId) &&
                Objects.equal(selectQTL, event.selectQTL) &&
                Objects.equal(baseInfo, event.baseInfo) &&
                Objects.equal(structure, event.structure);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(condition, selectSnp, selectIndel, firstHierarchyQtlId, selectQTL, baseInfo, structure);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("condition", condition)
                .add("selectSnp", selectSnp)
                .add("selectIndel", selectIndel)
                .add("firstHierarchyQtlId", firstHierarchyQtlId)
                .add("selectQTL", selectQTL)
                .add("baseInfo", baseInfo)
                .add("structure", structure)
                .omitNullValues()
                .toString();
    }

}
