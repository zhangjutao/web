package com.gooalgene.iqgs.entity;

import com.gooalgene.common.DataEntity;

/**
 * Created by sauldong on 2017/10/16.
 */
public class DNAGenSequence extends DataEntity<DNAGenSequence> {
    private String geneId;
    private String transcriptId;
    private String type;
    private String sequence;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
