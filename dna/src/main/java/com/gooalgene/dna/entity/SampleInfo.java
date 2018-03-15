package com.gooalgene.dna.entity;

import com.gooalgene.common.DataEntity;

import java.math.BigDecimal;

public class SampleInfo extends DataEntity<SampleInfo> {

    private String runNo;

    private String scientificName;

    private String sampleId;

    private String strainName;

    private String locality;

    private String preservationLocation;

    private String type;

    private String environment;

    private String materials;

    private String treat;

    private String time;

    private String taxonomy;

    private String myceliaPhenotype;

    private String myceliaDiameter;

    private String myceliaColor;

    private Float myceliumGrowthRate;

    private String gillPhenotype;

    private String gillColor;

    private String sporesColor;

    private String sporesShape;

    private String clampConnection;

    private Float pileusLength;

    private Float pileusWidth;

    private Float pileusThickness;

    private String pileusPhenotype;

    private String pileusColor;

    private Float stipeLength;

    private Float stipeDiameter;

    private String stipePhenotype;

    private String stipeColor;

    private Float yield;

    private String fruitbodyColor;

    private String fruitbodyType;

    private String illumination;

    private String collarium;

    private String volva;

    private String velum;

    private String sclerotium;

    private String strainMedium;

    private String mainSubstrate;

    private String afterRipeningStage;

    private String primordialStimulationFruitbody;

    private String reproductiveMode;

    private String lifestyle;

    private String preservation;

    private String domestication;

    private String nuclearPhase;

    private String matingType;

    public SampleInfo(String runNo, String scientificName, String sampleId, String strainName, String locality, String preservationLocation, String type, String environment, String materials, String treat, String time, String taxonomy, String myceliaPhenotype, String myceliaDiameter, String myceliaColor, Float myceliumGrowthRate, String gillPhenotype, String gillColor, String sporesColor, String sporesShape, String clampConnection, Float pileusLength, Float pileusWidth, Float pileusThickness, String pileusPhenotype, String pileusColor, Float stipeLength, Float stipeDiameter, String stipePhenotype, String stipeColor, Float yield, String fruitbodyColor, String fruitbodyType, String illumination, String collarium, String volva, String velum, String sclerotium, String strainMedium, String mainSubstrate, String afterRipeningStage, String primordialStimulationFruitbody, String reproductiveMode, String lifestyle, String preservation, String domestication, String nuclearPhase, String matingType) {
        this.runNo = runNo;
        this.scientificName = scientificName;
        this.sampleId = sampleId;
        this.strainName = strainName;
        this.locality = locality;
        this.preservationLocation = preservationLocation;
        this.type = type;
        this.environment = environment;
        this.materials = materials;
        this.treat = treat;
        this.time = time;
        this.taxonomy = taxonomy;
        this.myceliaPhenotype = myceliaPhenotype;
        this.myceliaDiameter = myceliaDiameter;
        this.myceliaColor = myceliaColor;
        this.myceliumGrowthRate = myceliumGrowthRate;
        this.gillPhenotype = gillPhenotype;
        this.gillColor = gillColor;
        this.sporesColor = sporesColor;
        this.sporesShape = sporesShape;
        this.clampConnection = clampConnection;
        this.pileusLength = pileusLength;
        this.pileusWidth = pileusWidth;
        this.pileusThickness = pileusThickness;
        this.pileusPhenotype = pileusPhenotype;
        this.pileusColor = pileusColor;
        this.stipeLength = stipeLength;
        this.stipeDiameter = stipeDiameter;
        this.stipePhenotype = stipePhenotype;
        this.stipeColor = stipeColor;
        this.yield = yield;
        this.fruitbodyColor = fruitbodyColor;
        this.fruitbodyType = fruitbodyType;
        this.illumination = illumination;
        this.collarium = collarium;
        this.volva = volva;
        this.velum = velum;
        this.sclerotium = sclerotium;
        this.strainMedium = strainMedium;
        this.mainSubstrate = mainSubstrate;
        this.afterRipeningStage = afterRipeningStage;
        this.primordialStimulationFruitbody = primordialStimulationFruitbody;
        this.reproductiveMode = reproductiveMode;
        this.lifestyle = lifestyle;
        this.preservation = preservation;
        this.domestication = domestication;
        this.nuclearPhase = nuclearPhase;
        this.matingType = matingType;
    }

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

    public Float getMyceliumGrowthRate() {
        return myceliumGrowthRate;
    }

    public void setMyceliumGrowthRate(Float myceliumGrowthRate) {
        this.myceliumGrowthRate = myceliumGrowthRate;
    }

    public String getGillPhenotype() {
        return gillPhenotype;
    }

    public void setGillPhenotype(String gillPhenotype) {
        this.gillPhenotype = gillPhenotype == null ? null : gillPhenotype.trim();
    }

    public String getGillColor() {
        return gillColor;
    }

    public void setGillColor(String gillColor) {
        this.gillColor = gillColor == null ? null : gillColor.trim();
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

    public Float getPileusLength() {
        return pileusLength;
    }

    public void setPileusLength(Float pileusLength) {
        this.pileusLength = pileusLength;
    }

    public Float getPileusWidth() {
        return pileusWidth;
    }

    public void setPileusWidth(Float pileusWidth) {
        this.pileusWidth = pileusWidth;
    }

    public Float getPileusThickness() {
        return pileusThickness;
    }

    public void setPileusThickness(Float pileusThickness) {
        this.pileusThickness = pileusThickness;
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

    public Float getStipeLength() {
        return stipeLength;
    }

    public void setStipeLength(Float stipeLength) {
        this.stipeLength = stipeLength;
    }

    public Float getStipeDiameter() {
        return stipeDiameter;
    }

    public void setStipeDiameter(Float stipeDiameter) {
        this.stipeDiameter = stipeDiameter;
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

    public Float getYield() {
        return yield;
    }

    public void setYield(Float yield) {
        this.yield = yield;
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
}