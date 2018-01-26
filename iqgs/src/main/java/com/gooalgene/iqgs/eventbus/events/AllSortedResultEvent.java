package com.gooalgene.iqgs.eventbus.events;

import com.gooalgene.iqgs.entity.Tissue;
import com.gooalgene.iqgs.entity.sort.SortedResult;
import com.google.common.base.Objects;

import java.util.List;

public class AllSortedResultEvent {
    /**
     * 基因ID集合
     */
    private List<String> geneIdList;
    /**
     * 已选中二级组织集合
     */
    private Tissue tissue;
    /**
     * 下拉选中的性状ID值
     */
    private Integer traitCategoryId;
    /**
     * 排序结果
     */
    private List<SortedResult> sortedResult;

    public AllSortedResultEvent(List<String> geneIdList, Tissue tissue, Integer traitCategoryId, List<SortedResult> sortedResult) {
        this.geneIdList = geneIdList;
        this.tissue = tissue;
        this.traitCategoryId = traitCategoryId;
        this.sortedResult = sortedResult;
    }

    public List<SortedResult> getSortedResult() {
        return sortedResult;
    }

    public void setSortedResult(List<SortedResult> sortedResult) {
        this.sortedResult = sortedResult;
    }

    public List<String> getGeneIdList() {
        return geneIdList;
    }

    public void setGeneIdList(List<String> geneIdList) {
        this.geneIdList = geneIdList;
    }

    public Tissue getTissue() {
        return tissue;
    }

    public void setTissue(Tissue tissue) {
        this.tissue = tissue;
    }

    public Integer getTraitCategoryId() {
        return traitCategoryId;
    }

    public void setTraitCategoryId(Integer traitCategoryId) {
        this.traitCategoryId = traitCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllSortedResultEvent that = (AllSortedResultEvent) o;
        return Objects.equal(geneIdList, that.geneIdList) &&
                Objects.equal(tissue, that.tissue) &&
                Objects.equal(traitCategoryId, that.traitCategoryId);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(geneIdList, tissue, traitCategoryId);
    }
}
