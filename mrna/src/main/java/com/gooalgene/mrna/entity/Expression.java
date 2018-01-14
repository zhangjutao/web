package com.gooalgene.mrna.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

/**
 * Created by Administrator on 2017/08/05.
 */
@Document
public class Expression {

    @Id
    private String id;

    private String gene;

    private List<SampleRun> samplerun;

    public Expression(String id, String gene, List<SampleRun> samplerun) {
        this.id = id;
        this.gene = gene;
        this.samplerun = samplerun;
    }

    public Expression() {
    }

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

    public List<SampleRun> getSamplerun() {
        return samplerun;
    }

    public void setSamplerun(List<SampleRun> samplerun) {
        this.samplerun = samplerun;
    }
}
