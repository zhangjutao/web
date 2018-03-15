package com.gooalgene.dna.entity;

import com.gooalgene.common.DataEntity;

public class SampleInfo extends DataEntity<SampleInfo> {

    /**
     * 样本编号
     */
    private String runNo;

    /**
     * 物种名称
     */
    private String scientificName;

    /**
     * 编号
     */
    private String sampleId;

    /**
     * 菌株名称
     */
    private String strainName;

    /**
     * 地理位置
     */
    private String locality;

    /**
     * 保藏地点
     */
    private String preservationLocation;

    /**
     * 类型
     */
    private String type;

    /**
     * 培养环境
     */
    private String environment;

    /**
     * 材料
     */
    private String materials;

    /**
     * 处理
     */
    private String treat;

    /**
     * 时间
     */
    private String time;

    /**
     * 分类地位
     */
    private String taxonomy;

    /**
     * 菌丝形态
     */
    private String myceliaPhenotype;

    /**
     * 菌丝直径
     */
    private String myceliaDiameter;

    /**
     * 菌丝颜色
     */
    private String myceliaColor;

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
    private String keywords;//后台管理搜索

    public SampleInfo() {
        super();
    }

    public String getRunNo() {
        return runNo;
    }

    public void setRunNo(String runNo) {
        this.runNo = runNo == null ? null : runNo.trim();
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName == null ? null : scientificName.trim();
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId == null ? null : sampleId.trim();
    }

    public String getStrainName() {
        return strainName;
    }

    public void setStrainName(String strainName) {
        this.strainName = strainName == null ? null : strainName.trim();
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality == null ? null : locality.trim();
    }

    public String getPreservationLocation() {
        return preservationLocation;
    }

    public void setPreservationLocation(String preservationLocation) {
        this.preservationLocation = preservationLocation == null ? null : preservationLocation.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment == null ? null : environment.trim();
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials == null ? null : materials.trim();
    }

    public String getTreat() {
        return treat;
    }

    public void setTreat(String treat) {
        this.treat = treat == null ? null : treat.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy == null ? null : taxonomy.trim();
    }

    public String getMyceliaPhenotype() {
        return myceliaPhenotype;
    }

    public void setMyceliaPhenotype(String myceliaPhenotype) {
        this.myceliaPhenotype = myceliaPhenotype == null ? null : myceliaPhenotype.trim();
    }

    public String getMyceliaDiameter() {
        return myceliaDiameter;
    }

    public void setMyceliaDiameter(String myceliaDiameter) {
        this.myceliaDiameter = myceliaDiameter == null ? null : myceliaDiameter.trim();
    }

    public String getMyceliaColor() {
        return myceliaColor;
    }

    public void setMyceliaColor(String myceliaColor) {
        this.myceliaColor = myceliaColor == null ? null : myceliaColor.trim();
    }

    public String getSporesColor() {
        return sporesColor;
    }

    public void setSporesColor(String sporesColor) {
        this.sporesColor = sporesColor == null ? null : sporesColor.trim();
    }

    public String getSporesShape() {
        return sporesShape;
    }

    public void setSporesShape(String sporesShape) {
        this.sporesShape = sporesShape == null ? null : sporesShape.trim();
    }

    public String getClampConnection() {
        return clampConnection;
    }

    public void setClampConnection(String clampConnection) {
        this.clampConnection = clampConnection == null ? null : clampConnection.trim();
    }

    public String getPileusPhenotype() {
        return pileusPhenotype;
    }

    public void setPileusPhenotype(String pileusPhenotype) {
        this.pileusPhenotype = pileusPhenotype == null ? null : pileusPhenotype.trim();
    }

    public String getPileusColor() {
        return pileusColor;
    }

    public void setPileusColor(String pileusColor) {
        this.pileusColor = pileusColor == null ? null : pileusColor.trim();
    }

    public String getStipePhenotype() {
        return stipePhenotype;
    }

    public void setStipePhenotype(String stipePhenotype) {
        this.stipePhenotype = stipePhenotype == null ? null : stipePhenotype.trim();
    }

    public String getStipeColor() {
        return stipeColor;
    }

    public void setStipeColor(String stipeColor) {
        this.stipeColor = stipeColor == null ? null : stipeColor.trim();
    }

    public String getFruitbodyColor() {
        return fruitbodyColor;
    }

    public void setFruitbodyColor(String fruitbodyColor) {
        this.fruitbodyColor = fruitbodyColor == null ? null : fruitbodyColor.trim();
    }

    public String getFruitbodyType() {
        return fruitbodyType;
    }

    public void setFruitbodyType(String fruitbodyType) {
        this.fruitbodyType = fruitbodyType == null ? null : fruitbodyType.trim();
    }

    public String getIllumination() {
        return illumination;
    }

    public void setIllumination(String illumination) {
        this.illumination = illumination == null ? null : illumination.trim();
    }

    public String getCollarium() {
        return collarium;
    }

    public void setCollarium(String collarium) {
        this.collarium = collarium == null ? null : collarium.trim();
    }

    public String getVolva() {
        return volva;
    }

    public void setVolva(String volva) {
        this.volva = volva == null ? null : volva.trim();
    }

    public String getVelum() {
        return velum;
    }

    public void setVelum(String velum) {
        this.velum = velum == null ? null : velum.trim();
    }

    public String getSclerotium() {
        return sclerotium;
    }

    public void setSclerotium(String sclerotium) {
        this.sclerotium = sclerotium == null ? null : sclerotium.trim();
    }

    public String getStrainMedium() {
        return strainMedium;
    }

    public void setStrainMedium(String strainMedium) {
        this.strainMedium = strainMedium == null ? null : strainMedium.trim();
    }

    public String getMainSubstrate() {
        return mainSubstrate;
    }

    public void setMainSubstrate(String mainSubstrate) {
        this.mainSubstrate = mainSubstrate == null ? null : mainSubstrate.trim();
    }

    public String getAfterRipeningStage() {
        return afterRipeningStage;
    }

    public void setAfterRipeningStage(String afterRipeningStage) {
        this.afterRipeningStage = afterRipeningStage == null ? null : afterRipeningStage.trim();
    }

    public String getPrimordialStimulationFruitbody() {
        return primordialStimulationFruitbody;
    }

    public void setPrimordialStimulationFruitbody(String primordialStimulationFruitbody) {
        this.primordialStimulationFruitbody = primordialStimulationFruitbody == null ? null : primordialStimulationFruitbody.trim();
    }

    public String getReproductiveMode() {
        return reproductiveMode;
    }

    public void setReproductiveMode(String reproductiveMode) {
        this.reproductiveMode = reproductiveMode == null ? null : reproductiveMode.trim();
    }

    public String getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(String lifestyle) {
        this.lifestyle = lifestyle == null ? null : lifestyle.trim();
    }

    public String getPreservation() {
        return preservation;
    }

    public void setPreservation(String preservation) {
        this.preservation = preservation == null ? null : preservation.trim();
    }

    public String getDomestication() {
        return domestication;
    }

    public void setDomestication(String domestication) {
        this.domestication = domestication == null ? null : domestication.trim();
    }

    public String getNuclearPhase() {
        return nuclearPhase;
    }

    public void setNuclearPhase(String nuclearPhase) {
        this.nuclearPhase = nuclearPhase == null ? null : nuclearPhase.trim();
    }

    public String getMatingType() {
        return matingType;
    }

    public void setMatingType(String matingType) {
        this.matingType = matingType == null ? null : matingType.trim();
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}