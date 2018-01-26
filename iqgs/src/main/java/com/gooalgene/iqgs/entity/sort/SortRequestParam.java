package com.gooalgene.iqgs.entity.sort;

import com.gooalgene.iqgs.entity.Tissue;

import java.util.List;

/**
 * 排序请求HTTP请求体中参数
 */
public class SortRequestParam {
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

    private Integer pageNo;

    private Integer pageSize;

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

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
