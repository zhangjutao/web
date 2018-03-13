package com.gooalgene.qtl.listeners.events;

import com.google.common.base.Objects;

/**
 * Qtl搜索结果页面下载预加载事件
 * @param <T> 传入的目标对象
 */
public class QtlSearchResultEvent<T> {
    private T t;

    /**
     * 用户已选择的check box
     */
    private String checkedOption;

    public QtlSearchResultEvent(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public String getCheckedOption() {
        return checkedOption;
    }

    public void setCheckedOption(String checkedOption) {
        this.checkedOption = checkedOption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QtlSearchResultEvent<?> that = (QtlSearchResultEvent<?>) o;
        return Objects.equal(t, that.t);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(t);
    }
}
