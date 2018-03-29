package com.gooalgene.dna.entity.result;

/**
 * MongoDB中查询出来的最少的SNP的值，只包含pos、consequencetype
 *
 * @author Crabime
 */
public class MinimumSNPResult {

    private long pos;

    private String consequenceType;

    private int consequenceTypeColor;

    private int index;

    public MinimumSNPResult(long pos, String consequenceType, int index) {
        this.pos = pos;
        this.consequenceType = consequenceType;
        this.index = index;
    }

    public int getConsequenceTypeColor() {
        return consequenceTypeColor;
    }

    public void setConsequenceTypeColor(int consequenceTypeColor) {
        this.consequenceTypeColor = consequenceTypeColor;
    }

    public long getPos() {
        return pos;
    }

    public void setPos(long pos) {
        this.pos = pos;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getConsequenceType() {
        return consequenceType;
    }

    public void setConsequenceType(String consequenceType) {
        this.consequenceType = consequenceType;
    }
}
