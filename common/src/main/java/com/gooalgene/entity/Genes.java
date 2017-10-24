package com.gooalgene.entity;

import com.gooalgene.common.DataEntity;

/**
 * Created by ShiYun on 2017/8/9 0009.
 */
public class Genes extends DataEntity<Genes> {

    private String gene;

    private String geneName;

    private String functions;

    private String keywords;//搜索字段

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
}
