package com.gooalgene.iqgs.entity;

import com.gooalgene.common.DataEntity;

public class DNAGenOffset extends DataEntity<DNAGenOffset> {
    private String geneName;

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    private String geneId;
    private int length;
    private int offset;

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
