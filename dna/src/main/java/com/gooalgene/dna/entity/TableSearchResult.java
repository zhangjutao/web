package com.gooalgene.dna.entity;

import com.gooalgene.dna.dto.SNPDto;

import java.util.List;

/**
 * SNP/INDEL查询中表格搜索结果
 *
 * @author crabime
 */
public class TableSearchResult {

    /**
     * 表中当前页的所有SNP数据
     */
    private List<SNPDto> data;

    /**
     * 区间内所有SNP的总条数
     */
    private Long total;

    /**
     * 用户选中点在当前页的偏移量
     */
    private int offset;

    /**
     * 用户所选点所在的页码
     */
    private int pageNo;

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

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
