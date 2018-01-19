package com.gooalgene.iqgs.entity.condition;

import com.gooalgene.dna.entity.DNAGenStructure;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;

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
     * 高级搜索联动Search By Gene Name/ID以及Search By Function中传入的参数
     */
    private DNAGenBaseInfo geneInfo;

    /**
     * 高级搜索联动Search By Region中传入的参数
     */
    private DNAGenStructure geneStructure;

    private Integer pageNo;

    private Integer pageSize;

    public List<Integer> getFirstHierarchyQtlId() {
        return firstHierarchyQtlId;
    }

    public void setFirstHierarchyQtlId(List<Integer> firstHierarchyQtlId) {
        this.firstHierarchyQtlId = firstHierarchyQtlId;
    }

    public DNAGenBaseInfo getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(DNAGenBaseInfo geneInfo) {
        this.geneInfo = geneInfo;
    }

    public DNAGenStructure getGeneStructure() {
        return geneStructure;
    }

    public void setGeneStructure(DNAGenStructure geneStructure) {
        this.geneStructure = geneStructure;
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
