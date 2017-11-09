package com.gooalgene.iqgs.entity;

import com.gooalgene.common.DataEntity;

/**
 * Created by sauldong on 2017/10/16.
 */
public class DNAGenAnnoIpr extends DataEntity<DNAGenAnnoIpr> {
    private String geneId;
    private String iprEntry;
    private String iprAnno;

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public String getGeneId() {
        return geneId;
    }

    public String getIprEntry() {
        return iprEntry;
    }

    public void setIprEntry(String iprEntry) {
        this.iprEntry = iprEntry;
    }

    public String getIprAnno() {
        return iprAnno;
    }

    public void setIprAnno(String iprAnno) {
        this.iprAnno = iprAnno;
    }
}
