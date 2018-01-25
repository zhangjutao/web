package com.gooalgene.iqgs.entity.sort;

/**
 * create by Administrator on2018/1/25 0025
 */
public class SnpOrIndelScore {
    protected String geneId;
    protected String consequencetype;
    protected Integer score;
    protected Integer count;

    public String getConsequencetype() {
        return consequencetype;
    }

    public void setConsequencetype(String consequencetype) {
        this.consequencetype = consequencetype;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    
}
