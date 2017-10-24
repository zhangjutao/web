package com.gooalgene.entity;

import com.gooalgene.common.DataEntity;

/**
 * Created by ShiYun on 2017/7/6 0006.
 */
public class AdvanceInfo extends DataEntity<AdvanceInfo> {

    private String qtlName;

    private String plantTraitOntology;

    private String plantOntology;

    private String geneOntology;

    private String otherRelatedQtls;

    private String otherNamesQtl;

    public String getQtlName() {
        return qtlName;
    }

    public void setQtlName(String qtlName) {
        this.qtlName = qtlName;
    }

    public String getPlantTraitOntology() {
        return plantTraitOntology;
    }

    public void setPlantTraitOntology(String plantTraitOntology) {
        this.plantTraitOntology = plantTraitOntology;
    }

    public String getPlantOntology() {
        return plantOntology;
    }

    public void setPlantOntology(String plantOntology) {
        this.plantOntology = plantOntology;
    }

    public String getGeneOntology() {
        return geneOntology;
    }

    public void setGeneOntology(String geneOntology) {
        this.geneOntology = geneOntology;
    }

    public String getOtherRelatedQtls() {
        return otherRelatedQtls;
    }

    public void setOtherRelatedQtls(String otherRelatedQtls) {
        this.otherRelatedQtls = otherRelatedQtls;
    }

    public String getOtherNamesQtl() {
        return otherNamesQtl;
    }

    public void setOtherNamesQtl(String otherNamesQtl) {
        this.otherNamesQtl = otherNamesQtl;
    }
}
