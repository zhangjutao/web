package com.gooalgene.iqgs.eventbus.events;

import com.gooalgene.dna.entity.DNAGenStructure;
import com.gooalgene.iqgs.entity.condition.AdvanceSearchResultView;
import com.google.common.base.Objects;

import java.util.List;

public class AllRegionSearchResultEvent {
    private DNAGenStructure genStructure;
    private List<AdvanceSearchResultView> searchResultViews;

    public AllRegionSearchResultEvent(DNAGenStructure genStructure, List<AdvanceSearchResultView> searchResultViews) {
        this.genStructure = genStructure;
        this.searchResultViews = searchResultViews;
    }

    public DNAGenStructure getGenStructure() {
        return genStructure;
    }

    public void setGenStructure(DNAGenStructure genStructure) {
        this.genStructure = genStructure;
    }

    public List<AdvanceSearchResultView> getSearchResultViews() {
        return searchResultViews;
    }

    public void setSearchResultViews(List<AdvanceSearchResultView> searchResultViews) {
        this.searchResultViews = searchResultViews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllRegionSearchResultEvent that = (AllRegionSearchResultEvent) o;
        return Objects.equal(genStructure, that.genStructure) &&
                Objects.equal(searchResultViews, that.searchResultViews);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(genStructure, searchResultViews);
    }
}
