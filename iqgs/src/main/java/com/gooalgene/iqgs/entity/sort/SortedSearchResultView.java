package com.gooalgene.iqgs.entity.sort;

import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.GeneFPKM;

import java.util.List;
import java.util.Set;

public class SortedSearchResultView {
    private String id;

    /**
     * 基因基本信息
     */
    private DNAGenBaseInfo baseInfo;

    /**
     * 计算后的分值
     */
    private int score;
    /**
     * 搜索结果FPKM
     */
    private GeneFPKM fpkm;
    /**
     * 染色体类型
     */
    private String chromosome;
    /**
     * 基因位置
     */
    private String location;
    /**
     * 该基因包含的所有变异类型
     */
    private List<SnpScore> snpConsequenceType;
    /**
     * 该基因包含的所有插入类型
     */
    private List<IndelScore> indelConsequenceType;

    private List<String> scores;
    /**
     * 该基因包含的所有插入类型
     */
    private List<String> counts;

    /**
     * 该基因包含的所有数量性状类型
     */
    private Set<String> allQtl;

    public List<String> getScores() {
        return scores;
    }

    public void setScores(List<String> scores) {
        this.scores = scores;
    }

    public List<String> getCounts() {
        return counts;
    }

    public void setCounts(List<String> counts) {
        this.counts = counts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GeneFPKM getFpkm() {
        return fpkm;
    }

    public void setFpkm(GeneFPKM fpkm) {
        this.fpkm = fpkm;
    }

    public List<SnpScore> getSnpConsequenceType() {
        return snpConsequenceType;
    }

    public void setSnpConsequenceType(List<SnpScore> snpConsequenceType) {
        this.snpConsequenceType = snpConsequenceType;
    }

    public List<IndelScore> getIndelConsequenceType() {
        return indelConsequenceType;
    }

    public void setIndelConsequenceType(List<IndelScore> indelConsequenceType) {
        this.indelConsequenceType = indelConsequenceType;
    }

    public Set<String> getAllQtl() {
        return allQtl;
    }

    public void setAllQtl(Set<String> allQtl) {
        this.allQtl = allQtl;
    }

    public DNAGenBaseInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(DNAGenBaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
