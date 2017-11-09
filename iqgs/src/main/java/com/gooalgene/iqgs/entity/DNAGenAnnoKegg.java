package com.gooalgene.iqgs.entity;

import com.gooalgene.common.DataEntity;

/**
 * Created by sauldong on 2017/10/16.
 */
public class DNAGenAnnoKegg extends DataEntity<DNAGenAnnoKegg> {
    private String geneId;
    private String targetId;
    private String koEntry;
    private String koDefinition;

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public String getGeneId() {
        return geneId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getKoEntry() {
        return koEntry;
    }

    public void setKoEntry(String koEntry) {
        this.koEntry = koEntry;
    }

    public String getKoDefinition() {
        return koDefinition;
    }

    public void setKoDefinition(String koDefinition) {
        this.koDefinition = koDefinition;
    }
}
