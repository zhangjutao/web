package com.gooalgene.iqgs.entity;

import com.gooalgene.common.DataEntity;

/**
 * Created by sauldong on 2017/10/16.
 */
public class DNAGenAnnoGo extends DataEntity<DNAGenAnnoGo> {
    private String geneId;
    private String goEntry;
    private String anno;
    private String type;

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public String getGeneId() {
        return geneId;
    }

    public String getGoEntry() {
        return goEntry;
    }

    public void setGoEntry(String goEntry) {
        this.goEntry = goEntry;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
