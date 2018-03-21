package com.gooalgene.dna.entity;

import com.gooalgene.dna.entity.result.GroupCondition;

import java.util.List;

/**
 * SNP/INDEL查询条件，前端传入参数获取SNP/INDEL table数据时查询参数
 *
 * @author crabime
 */
public class SearchCondition {
    /**
     * SNP/INDEL
     */
    private String type;

    /**
     * 选择的consequenceType
     */
    private String ctype;

    /**
     * 如果是根据基因搜索，传入的基因
     */
    private String gene;

    /**
     * 下拉选择的染色体类型
     */
    private String chromosome;

    /**
     * 用户输入的起点位置
     */
    private Long start;

    /**
     * 用户输入的终点位置
     */
    private Long end;

    /**
     * 用户当前选取点在MongoDB中的索引值
     */
    private int index;

    /**
     * 用户所选择的群组
     */
    private List<GroupCondition> group;

    private int pageNo;

    private int pageSize;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getGene() {
        return gene;
    }

    public void setGene(String gene) {
        this.gene = gene;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public List<GroupCondition> getGroup() {
        return group;
    }

    public void setGroup(List<GroupCondition> group) {
        this.group = group;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
