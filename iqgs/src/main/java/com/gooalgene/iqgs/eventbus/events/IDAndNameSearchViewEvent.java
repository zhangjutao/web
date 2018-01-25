package com.gooalgene.iqgs.eventbus.events;

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

    public IDAndNameSearchViewEvent(List<Integer> id) {
        this.id = id;
    }

    public List<Integer> getId() {
        return id;
    }

    public void setId(List<Integer> id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IDAndNameSearchViewEvent that = (IDAndNameSearchViewEvent) o;
        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
}
