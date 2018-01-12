package com.gooalgene.iqgs.entity.condition;

import java.util.List;

/**
 * 基因表达量查询条件构成的bean
 * @author crabime
 */
public class GeneExpressionCondition {

    /**
     * 一级搜索选中的QTL ID集合
     */
    private List<Integer> firstHierarchyQtlId;

    private List<GeneExpressionConditionEntity> geneExpressionConditionEntities;

    private List<String> snpConsequenceType;

    private List<String> indelConsequenceType;

    private List<Integer> qtlId;

    /**
     * 高级搜索联动Search By Gene Name/ID中传入的参数
     */
    private String geneName;

    /**
     * 高级搜索联动Search By Gene Function中传入的参数
     */
    private String geneFunction;

    private Integer pageNo;

    private Integer pageSize;

    public List<Integer> getFirstHierarchyQtlId() {
        return firstHierarchyQtlId;
    }

    public void setFirstHierarchyQtlId(List<Integer> firstHierarchyQtlId) {
        this.firstHierarchyQtlId = firstHierarchyQtlId;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public String getGeneFunction() {
        return geneFunction;
    }

    public void setGeneFunction(String geneFunction) {
        this.geneFunction = geneFunction;
    }

    public List<GeneExpressionConditionEntity> getGeneExpressionConditionEntities() {
        return geneExpressionConditionEntities;
    }

    public void setGeneExpressionConditionEntities(List<GeneExpressionConditionEntity> geneExpressionConditionEntities) {
        this.geneExpressionConditionEntities = geneExpressionConditionEntities;
    }

    public List<String> getSnpConsequenceType() {
        return snpConsequenceType;
    }

    public void setSnpConsequenceType(List<String> snpConsequenceType) {
        this.snpConsequenceType = snpConsequenceType;
    }

    public List<String> getIndelConsequenceType() {
        return indelConsequenceType;
    }

    public void setIndelConsequenceType(List<String> indelConsequenceType) {
        this.indelConsequenceType = indelConsequenceType;
    }

    public List<Integer> getQtlId() {
        return qtlId;
    }

    public void setQtlId(List<Integer> qtlId) {
        this.qtlId = qtlId;
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
