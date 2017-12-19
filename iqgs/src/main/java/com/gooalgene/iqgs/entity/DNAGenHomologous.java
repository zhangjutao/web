package com.gooalgene.iqgs.entity;

import com.gooalgene.common.DataEntity;

/**
 * Created by sauldong on 2017/10/17.
 */
public class DNAGenHomologous extends DataEntity<DNAGenHomologous> {
    private String geneId;
    private String orthologSpecies;
    private String orthologGeneId;
    private String orthologGeneDesc;
    private String relationship;

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public String getOrthologSpecies() {
        return orthologSpecies;
    }

    public void setOrthologSpecies(String orthologSpecies) {
        this.orthologSpecies = orthologSpecies;
    }

    public String getOrthologGeneId() {
        return orthologGeneId;
    }

    public void setOrthologGeneId(String orthologGeneId) {
        this.orthologGeneId = orthologGeneId;
    }

    public String getOrthologGeneDesc() {
        return orthologGeneDesc;
    }

    public void setOrthologGeneDesc(String orthologGeneDesc) {
        this.orthologGeneDesc = orthologGeneDesc;
    }


    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}
