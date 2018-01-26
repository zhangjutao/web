package com.gooalgene.iqgs.eventbus.events;

import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.google.common.base.Objects;

import java.util.List;

/**
 * 根据ID/NAME/FUNCTION查询EventBus事件
 */
public class IDAndNameSearchViewEvent {
    /**
     * gene_baseinfo表中ID主键集合
     */
    private List<Integer> id;

    private DNAGenBaseInfo baseInfo;


    public IDAndNameSearchViewEvent(List<Integer> id, DNAGenBaseInfo baseInfo) {
        this.id = id;
        this.baseInfo = baseInfo;
    }

    public List<Integer> getId() {
        return id;
    }

    public void setId(List<Integer> id) {
        this.id = id;
    }

    public DNAGenBaseInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(DNAGenBaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IDAndNameSearchViewEvent event = (IDAndNameSearchViewEvent) o;
        return Objects.equal(id, event.id) &&
                Objects.equal(baseInfo, event.baseInfo);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, baseInfo);
    }
}
