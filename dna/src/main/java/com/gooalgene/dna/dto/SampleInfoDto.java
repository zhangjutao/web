package com.gooalgene.dna.dto;

import java.util.List;

public class SampleInfoDto {
    public static final String EQ="eq";
    public static final String GT="gt";
    public static final String LT="lt";

    private String runNo;//run编号

    private List<String> runNos;

    private String scientificName;//物种名称

    private String sampleId;//编号

    private String strainName;//菌株名称

    private String preservationLocation;//保藏地点

    private String locality;//位置

    private String type;//类型

    private String environment;//培养环境

    private String materials;//材料

    private String treat;//处理

    private String time;//时间

    private String taxonomy;//分类地位


    private String myceliaPhenotype;//菌丝形态

    private String myceliaDiameter;//菌丝直径


    private String myceliaColor; //菌丝颜色


    private String keywords;

    /**
     * 孢子颜色
     */
    private String sporesColor;

    /**
     * 孢子形态
     */
    private String sporesShape;

    /**
     * 锁状联合
     */
    private String clampConnection;

    /**
     * 菌盖形态
     */
    private String pileusPhenotype;

    /**
     * 菌盖颜色
     */
    private String pileusColor;

    /**
     * 菌柄形态
     */
    private String stipePhenotype;

    /**
     * 菌柄颜色
     */
    private String stipeColor;

    //private Float yield;

    /**
     * 子实体颜色
     */
    private String fruitbodyColor;

    /**
     * 子实体形态
     */
    private String fruitbodyType;

    /**
     * 光照
     */
    private String illumination;

    /**
     * 菌环
     */
    private String collarium;

    /**
     * 菌托
     */
    private String volva;

    /**
     * 菌幕
     */
    private String velum;

    /**
     * 菌核
     */
    private String sclerotium;

    /**
     * 菌种培养基
     */
    private String strainMedium;

    /**
     * 主要栽培基质
     */
    private String mainSubstrate;

    /**
     * 后熟期
     */
    private String afterRipeningStage;

    /**
     * 原基刺激以及子实体
     */
    private String primordialStimulationFruitbody;

    /**
     * 生殖方式
     */
    private String reproductiveMode;

    /**
     * 生活方式
     */
    private String lifestyle;

    /**
     * 保藏方法
     */
    private String preservation;

    /**
     * 驯化
     */
    private String domestication;

    /**
     * 核相
     */
    private String nuclearPhase;

    /**
     * 交配型
     */
    private String matingType;
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<String> getRunNos() {
        return runNos;
    }

    public void setRunNos(List<String> runNos) {
        this.runNos = runNos;
    }

    public String getRunNo() {
        return runNo;
    }

    public void setRunNo(String runNo) {
        this.runNo = runNo;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getStrainName() {
        return strainName;
    }

    public void setStrainName(String strainName) {
        this.strainName = strainName;
    }

    public String getPreservationLocation() {
        return preservationLocation;
    }

    public void setPreservationLocation(String preservationLocation) {
        this.preservationLocation = preservationLocation;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getTreat() {
        return treat;
    }

    public void setTreat(String treat) {
        this.treat = treat;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public String getMyceliaPhenotype() {
        return myceliaPhenotype;
    }

    public void setMyceliaPhenotype(String myceliaPhenotype) {
        this.myceliaPhenotype = myceliaPhenotype;
    }

    public String getMyceliaDiameter() {
        return myceliaDiameter;
    }

    public void setMyceliaDiameter(String myceliaDiameter) {
        this.myceliaDiameter = myceliaDiameter;
    }

    public String getMyceliaColor() {
        return myceliaColor;
    }

    public void setMyceliaColor(String myceliaColor) {
        this.myceliaColor = myceliaColor;
    }

    public String getSporesColor() {
        return sporesColor;
    }

    public void setSporesColor(String sporesColor) {
        this.sporesColor = sporesColor;
    }

    public String getSporesShape() {
        return sporesShape;
    }

    public void setSporesShape(String sporesShape) {
        this.sporesShape = sporesShape;
    }

    public String getClampConnection() {
        return clampConnection;
    }

    public void setClampConnection(String clampConnection) {
        this.clampConnection = clampConnection;
    }

    public String getPileusPhenotype() {
        return pileusPhenotype;
    }

    public void setPileusPhenotype(String pileusPhenotype) {
        this.pileusPhenotype = pileusPhenotype;
    }

    public String getPileusColor() {
        return pileusColor;
    }

    public void setPileusColor(String pileusColor) {
        this.pileusColor = pileusColor;
    }

    public String getStipePhenotype() {
        return stipePhenotype;
    }

    public void setStipePhenotype(String stipePhenotype) {
        this.stipePhenotype = stipePhenotype;
    }

    public String getStipeColor() {
        return stipeColor;
    }

    public void setStipeColor(String stipeColor) {
        this.stipeColor = stipeColor;
    }

    public String getFruitbodyColor() {
        return fruitbodyColor;
    }

    public void setFruitbodyColor(String fruitbodyColor) {
        this.fruitbodyColor = fruitbodyColor;
    }

    public String getFruitbodyType() {
        return fruitbodyType;
    }

    public void setFruitbodyType(String fruitbodyType) {
        this.fruitbodyType = fruitbodyType;
    }

    public String getIllumination() {
        return illumination;
    }

    public void setIllumination(String illumination) {
        this.illumination = illumination;
    }

    public String getCollarium() {
        return collarium;
    }

    public void setCollarium(String collarium) {
        this.collarium = collarium;
    }

    public String getVolva() {
        return volva;
    }

    public void setVolva(String volva) {
        this.volva = volva;
    }

    public String getVelum() {
        return velum;
    }

    public void setVelum(String velum) {
        this.velum = velum;
    }

    public String getSclerotium() {
        return sclerotium;
    }

    public void setSclerotium(String sclerotium) {
        this.sclerotium = sclerotium;
    }

    public String getStrainMedium() {
        return strainMedium;
    }

    public void setStrainMedium(String strainMedium) {
        this.strainMedium = strainMedium;
    }

    public String getMainSubstrate() {
        return mainSubstrate;
    }

    public void setMainSubstrate(String mainSubstrate) {
        this.mainSubstrate = mainSubstrate;
    }

    public String getAfterRipeningStage() {
        return afterRipeningStage;
    }

    public void setAfterRipeningStage(String afterRipeningStage) {
        this.afterRipeningStage = afterRipeningStage;
    }

    public String getPrimordialStimulationFruitbody() {
        return primordialStimulationFruitbody;
    }

    public void setPrimordialStimulationFruitbody(String primordialStimulationFruitbody) {
        this.primordialStimulationFruitbody = primordialStimulationFruitbody;
    }

    public String getReproductiveMode() {
        return reproductiveMode;
    }

    public void setReproductiveMode(String reproductiveMode) {
        this.reproductiveMode = reproductiveMode;
    }

    public String getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(String lifestyle) {
        this.lifestyle = lifestyle;
    }

    public String getPreservation() {
        return preservation;
    }

    public void setPreservation(String preservation) {
        this.preservation = preservation;
    }

    public String getDomestication() {
        return domestication;
    }

    public void setDomestication(String domestication) {
        this.domestication = domestication;
    }

    public String getNuclearPhase() {
        return nuclearPhase;
    }

    public void setNuclearPhase(String nuclearPhase) {
        this.nuclearPhase = nuclearPhase;
    }

    public String getMatingType() {
        return matingType;
    }

    public void setMatingType(String matingType) {
        this.matingType = matingType;
    }
}
