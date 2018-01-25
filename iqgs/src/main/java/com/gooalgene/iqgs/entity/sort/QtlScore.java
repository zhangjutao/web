package com.gooalgene.iqgs.entity.sort;

/**
 * create by Administrator on2018/1/25 0025
 */
public class QtlScore {
    private String geneId;
    private String qtlName;
    //private Integer qtlScore;

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    /*public Integer getQtlScore() {
        return qtlScore;
    }

    public void setQtlScore(Integer qtlScore) {
        this.qtlScore = qtlScore;
    }*/

    public String getQtlName() {
        return qtlName;
    }

    public void setQtlName(String qtlName) {
        this.qtlName = qtlName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QtlScore)) return false;

        QtlScore qtlScore = (QtlScore) o;

        if (geneId != null ? !geneId.equals(qtlScore.geneId) : qtlScore.geneId != null) return false;
        return qtlName != null ? qtlName.equals(qtlScore.qtlName) : qtlScore.qtlName == null;
    }

    @Override
    public int hashCode() {
        int result = geneId != null ? geneId.hashCode() : 0;
        result = 31 * result + (qtlName != null ? qtlName.hashCode() : 0);
        return result;
    }
}
