package com.gooalgene.entity;

import com.gooalgene.common.DataEntity;

/**
 * Created by 陈冬 on 2017/8/22.
 */
public class MrnaGens extends DataEntity<MrnaGens> {
    private String gene;
    private String geneName;
    private String functions;

    private String keywords;//当all查询时才存在

    public String getGene() {
        return gene;
    }

    public void setGene(String gene) {
        this.gene = gene;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public String getFunctions() {
        return functions;
    }

    public void setFunctions(String functions) {
        this.functions = functions;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "MrnaGens{" +
                "gene='" + gene + '\'' +
                ", geneName='" + geneName + '\'' +
                ", functions='" + functions + '\'' +
                '}';
    }
}
