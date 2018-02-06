package com.gooalgene.iqgs.eventbus.events;

import com.gooalgene.iqgs.entity.AdvanceSearchType;
import com.gooalgene.iqgs.entity.condition.GeneExpressionConditionEntity;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * 获取所有高级搜索结果事件
 */
public class AllAdvanceSearchViewEvent<T> {
    private List<GeneExpressionConditionEntity> condition;
    private List<Integer> selectSnp;
    private List<Integer> selectIndel;
    private List<Integer> firstHierarchyQtlId;
    private T t;
    private AdvanceSearchType type;

    public AllAdvanceSearchViewEvent(List<GeneExpressionConditionEntity> condition,
                                     List<Integer> selectSnp, List<Integer> selectIndel,
                                     List<Integer> firstHierarchyQtlId, T t, AdvanceSearchType type) {
        this.condition = condition;
        this.selectSnp = selectSnp;
        this.selectIndel = selectIndel;
        this.firstHierarchyQtlId = firstHierarchyQtlId;
        this.type = type;
        this.t = t;
    }

    public List<GeneExpressionConditionEntity> getCondition() {
        return condition;
    }

    public void setCondition(List<GeneExpressionConditionEntity> condition) {
        this.condition = condition;
    }

    public List<Integer> getSelectSnp() {
        return selectSnp;
    }

    public void setSelectSnp(List<Integer> selectSnp) {
        this.selectSnp = selectSnp;
    }

    public List<Integer> getSelectIndel() {
        return selectIndel;
    }

    public void setSelectIndel(List<Integer> selectIndel) {
        this.selectIndel = selectIndel;
    }

    public List<Integer> getFirstHierarchyQtlId() {
        return firstHierarchyQtlId;
    }

    public void setFirstHierarchyQtlId(List<Integer> firstHierarchyQtlId) {
        this.firstHierarchyQtlId = firstHierarchyQtlId;
    }

    public AdvanceSearchType getType() {
        return type;
    }

    public void setType(AdvanceSearchType type) {
        this.type = type;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllAdvanceSearchViewEvent<?> event = (AllAdvanceSearchViewEvent<?>) o;
        return Objects.equal(condition, event.condition) &&
                Objects.equal(selectSnp, event.selectSnp) &&
                Objects.equal(selectIndel, event.selectIndel) &&
                Objects.equal(firstHierarchyQtlId, event.firstHierarchyQtlId) &&
                Objects.equal(t, event.t);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(condition, selectSnp, selectIndel, firstHierarchyQtlId, t);
    }
}
