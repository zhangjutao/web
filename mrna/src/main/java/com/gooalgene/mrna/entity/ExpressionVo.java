package com.gooalgene.mrna.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

/**
 * Created by Administrator on 2017/08/05.
 */
@Document
public class ExpressionVo {

    @Id
    private String id;

    private String gene;

    private SampleRun samplerun;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGene() {
        return gene;
    }

    public void setGene(String gene) {
        this.gene = gene;
    }

    public SampleRun getSamplerun() {
        return samplerun;
    }

    public void setSamplerun(SampleRun samplerun) {
        this.samplerun = samplerun;
    }
}
