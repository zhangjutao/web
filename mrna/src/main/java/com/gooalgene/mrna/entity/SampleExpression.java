package com.gooalgene.mrna.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

/**
 * Created by ShiYun on 2017/8/16 0016.
 */
@Document
public class SampleExpression {

    @Id
    private String id;

    private String gene;

    private List<Run> samples;

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

    public List<Run> getSamples() {
        return samples;
    }

    public void setSamples(List<Run> samples) {
        this.samples = samples;
    }
}
