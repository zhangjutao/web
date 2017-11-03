package com.gooalgene.mrna.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

/**
 * Created by ShiYun on 2017/8/4 0004.
 */
@Document
public class Comparison {

    @Id
    private String id;

    private String gene;

    private List<String> diffs_list;

    private List<Diff> diffs;

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

    public List<String> getDiffs_list() {
        return diffs_list;
    }

    public void setDiffs_list(List<String> diffs_list) {
        this.diffs_list = diffs_list;
    }

    public List<Diff> getDiffs() {
        return diffs;
    }

    public void setDiffs(List<Diff> diffs) {
        this.diffs = diffs;
    }

    @Override
    public String toString() {
        return "Comparison{" +
                "id='" + id + '\'' +
                ", gene='" + gene + '\'' +
                ", diffs_list=" + diffs_list +
                ", diffs=" + diffs.size() +
                '}';
    }
}
