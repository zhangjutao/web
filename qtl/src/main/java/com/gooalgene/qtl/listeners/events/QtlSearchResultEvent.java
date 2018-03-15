package com.gooalgene.qtl.listeners.events;

import com.google.common.base.Objects;

/**
 * Qtl搜索结果页面下载预加载事件
 * @param <T> 传入的目标对象
 */
public class QtlSearchResultEvent<T> {
    private T t;

    /**
     * 如果用户选择的不是All而是Trait或其它时，qtl对象本身是不存在keyword属性的
     */
    private String alternativeKeyWord;

    public QtlSearchResultEvent(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public String getAlternativeKeyWord() {
        return alternativeKeyWord;
    }

    public void setAlternativeKeyWord(String alternativeKeyWord) {
        this.alternativeKeyWord = alternativeKeyWord;
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
