package com.gooalgene.dna.dto;

public class DataExportCondition {

    private SampleInfoDto condition;

    private String titles;

    private String judgeAllele;

    public SampleInfoDto getCondition() {
        return condition;
    }

    public void setCondition(SampleInfoDto condition) {
        this.condition = condition;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getJudgeAllele() {
        return judgeAllele;
    }

    public void setJudgeAllele(String judgeAllele) {
        this.judgeAllele = judgeAllele;
    }
}
