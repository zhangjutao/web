package com.gooalgene.dna.entity;

import com.gooalgene.common.DataEntity;

/**
 * Created by Administrator on 2017-10-16.
 */
public class DNAGenStructure extends DataEntity<DNAGenStructure> {
    private String chromosome;
    private String geneId;
    private String transcriptId;
    private String feature;
    private long start;
    private long end;
    private long length;
    private String strand;
    private long maxLength;
    private long offset;

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public String getTranscriptId() {
        return transcriptId;
    }

    public void setTranscriptId(String transcriptId) {
        this.transcriptId = transcriptId;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long lenth) {
        this.length = lenth;
    }

    public String getStrand() {
        return strand;
    }

    public void setStrand(String strand) {
        this.strand = strand;
    }

    public long getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(long maxLength) {
        this.maxLength = maxLength;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof DNAGenStructure)) return false;
        DNAGenStructure structure = (DNAGenStructure) obj;
        if (structure.getGeneId() == null) return false;
        return structure.getGeneId().equals(this.geneId);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.geneId.hashCode();
        return result;
    }
}
