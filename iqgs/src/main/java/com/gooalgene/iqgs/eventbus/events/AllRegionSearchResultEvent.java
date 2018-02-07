package com.gooalgene.iqgs.eventbus.events;

import com.google.common.base.Objects;

public class AllRegionSearchResultEvent {
    private String chromosome;
    private int start;
    private int end;

    public AllRegionSearchResultEvent(String chromosome, int start, int end) {
        this.chromosome = chromosome;
        this.start = start;
        this.end = end;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllRegionSearchResultEvent event = (AllRegionSearchResultEvent) o;
        return start == event.start &&
                end == event.end &&
                Objects.equal(chromosome, event.chromosome);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(chromosome, start, end);
    }
}
