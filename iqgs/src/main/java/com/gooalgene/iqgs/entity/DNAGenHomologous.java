package com.gooalgene.iqgs.entity;

import com.gooalgene.common.DataEntity;

/**
 * Created by sauldong on 2017/10/17.
 */
public class DNAGenHomologous extends DataEntity<DNAGenHomologous> {
    private String geneId;
    private String arabiId;
    private String arabiSymbol;
    private String arabiDefinition;

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public String getArabiId() {
        return arabiId;
    }

    public void setArabiId(String arabiId) {
        this.arabiId = arabiId;
    }

    public String getArabiSymbol() {
        return arabiSymbol;
    }

    public void setArabiSymbol(String arabiSymbol) {
        this.arabiSymbol = arabiSymbol;
    }

    public String getArabiDefinition() {
        return arabiDefinition;
    }

    public void setArabiDefinition(String arabiDefinition) {
        this.arabiDefinition = arabiDefinition;
    }
}
