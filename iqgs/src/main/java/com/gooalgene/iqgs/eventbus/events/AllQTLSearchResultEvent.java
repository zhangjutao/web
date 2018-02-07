package com.gooalgene.iqgs.eventbus.events;

import com.google.common.base.Objects;

import java.util.List;

public class AllQTLSearchResultEvent {
    private List<Integer> selectedQtl;

    public AllQTLSearchResultEvent(List<Integer> selectedQtl) {
        this.selectedQtl = selectedQtl;
    }

    public List<Integer> getSelectedQtl() {
        return selectedQtl;
    }

    public void setSelectedQtl(List<Integer> selectedQtl) {
        this.selectedQtl = selectedQtl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllQTLSearchResultEvent that = (AllQTLSearchResultEvent) o;
        return Objects.equal(selectedQtl, that.selectedQtl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(selectedQtl);
    }
}
