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

    private List<SNPDto> data;

    private Long total;

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
