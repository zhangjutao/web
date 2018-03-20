package com.gooalgene.dna.entity;

import com.gooalgene.dna.dto.SNPDto;

import java.util.List;
import java.util.Set;

/**
 * SNP/INDEL查询中表格搜索结果
 *
 * @author crabime
 */
public class TableSearchResult {

    /**
     * 如果查询结果中有基因，该集合不为空
     */
    private Set<String> geneIds;

    private List<SNPDto> data;

    private Long total;

    public Set<String> getGeneIds() {
        return geneIds;
    }

    public void setGeneIds(Set<String> geneIds) {
        this.geneIds = geneIds;
    }

    public List<SNPDto> getData() {
        return data;
    }

    public void setData(List<SNPDto> data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
