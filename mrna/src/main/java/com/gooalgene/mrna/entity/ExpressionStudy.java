package com.gooalgene.mrna.entity;


import com.gooalgene.common.BaseEntity;
import com.gooalgene.entity.Study;


public class ExpressionStudy extends BaseEntity<ExpressionStudy> {


    private String id;

    private String gene;

    private SampleRun samplerun;

    private Study study;

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

   public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void preInsert() {

    }

    @Override
    public void preUpdate() {

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
