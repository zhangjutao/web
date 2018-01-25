package com.gooalgene.iqgs.entity.sort;

/**
 * create by Administrator on2018/1/25 0025
 */
public class IndelScore extends SnpOrIndelScore{
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IndelScore)) return false;

        IndelScore that = (IndelScore) o;

        if (geneId != null ? !geneId.equals(that.geneId) : that.geneId != null) return false;
        if (consequencetype != null ? !consequencetype.equals(that.consequencetype) : that.consequencetype != null)
            return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;
        return count != null ? count.equals(that.count) : that.count == null;
    }

    @Override
    public int hashCode() {
        int result = geneId != null ? geneId.hashCode() : 0;
        result = 31 * result + (consequencetype != null ? consequencetype.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        return result;
    }
}
