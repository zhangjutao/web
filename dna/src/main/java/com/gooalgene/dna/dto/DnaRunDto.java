package com.gooalgene.dna.dto;

public class DnaRunDto{
    public static final String EQ="eq";
    public static final String GT="gt";
    public static final String LT="lt";

    private String runNo;//run编号

    private String species;//物种

    private String sampleName;//样品名

    private String cultivar;//品种名

    private String plantName;//品种名称

    private String locality;//位置

    private CompareHelper protein;//蛋白质含量 %

    private String protein_min;
    private String protein_max;

    private CompareHelper oil;//含油量 %

    private String oil_min;
    private String oil_max;

    private CompareHelper linoleic;//亚油酸 %
    private String linoleic_min;
    private String linoleic_max;

    private CompareHelper linolenic;//亚麻酸 %
    private String linolenic_min;
    private String linolenic_max;

    private CompareHelper oleic;//油酸 %
    private String oleic_min;
    private String oleic_max;

    private CompareHelper palmitic;//软脂酸 %
    private String palmitic_min;
    private String palmitic_max;

    private CompareHelper stearic;//硬脂酸 %
    private String stearic_min;
    private String stearic_max;

    private CompareHelper height;//株高 cm
    private String height_min;
    private String height_max;

    private String flowerColor;//花色

    private String hilumColor;//种脐色

    private String podColor;//荚色

    private String pubescenceColor;//茸毛色

    private String seedCoatColor;//种皮色

    private String cotyledonColor;//子叶色

    private CompareHelper weightPer100seeds;//百粒重 g
    private String weightPer100seeds_min;
    private String weightPer100seeds_max;

    private CompareHelper upperLeafletLength;//顶端小叶长度 mm
    private String upperLeafletLength_min;
    private String upperLeafletLength_max;

    private String floweringDate;//开花日期（月，日）

    private String maturityDate;//成熟期组

    private CompareHelper yield;//产量（Mg/ha）

    private String group; //组别

    private String yield_min;
    private String yield_max;

    public String getRunNo() {
        return runNo;
    }

    public void setRunNo(String runNo) {
        this.runNo = runNo;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getCultivar() {
        return cultivar;
    }

    public void setCultivar(String cultivar) {
        this.cultivar = cultivar;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public CompareHelper getProtein() {
        return protein;
    }

    public void setProtein(CompareHelper protein) {
        this.protein = protein;
    }

    public String getProtein_min() {
        return protein_min;
    }

    public void setProtein_min(String protein_min) {
        this.protein_min = protein_min;
    }

    public String getProtein_max() {
        return protein_max;
    }

    public void setProtein_max(String protein_max) {
        this.protein_max = protein_max;
    }

    public CompareHelper getOil() {
        return oil;
    }

    public void setOil(CompareHelper oil) {
        this.oil = oil;
    }

    public String getOil_min() {
        return oil_min;
    }

    public void setOil_min(String oil_min) {
        this.oil_min = oil_min;
    }

    public String getOil_max() {
        return oil_max;
    }

    public void setOil_max(String oil_max) {
        this.oil_max = oil_max;
    }

    public CompareHelper getLinoleic() {
        return linoleic;
    }

    public void setLinoleic(CompareHelper linoleic) {
        this.linoleic = linoleic;
    }

    public String getLinoleic_min() {
        return linoleic_min;
    }

    public void setLinoleic_min(String linoleic_min) {
        this.linoleic_min = linoleic_min;
    }

    public String getLinoleic_max() {
        return linoleic_max;
    }

    public void setLinoleic_max(String linoleic_max) {
        this.linoleic_max = linoleic_max;
    }

    public CompareHelper getLinolenic() {
        return linolenic;
    }

    public void setLinolenic(CompareHelper linolenic) {
        this.linolenic = linolenic;
    }

    public String getLinolenic_min() {
        return linolenic_min;
    }

    public void setLinolenic_min(String linolenic_min) {
        this.linolenic_min = linolenic_min;
    }

    public String getLinolenic_max() {
        return linolenic_max;
    }

    public void setLinolenic_max(String linolenic_max) {
        this.linolenic_max = linolenic_max;
    }

    public CompareHelper getOleic() {
        return oleic;
    }

    public void setOleic(CompareHelper oleic) {
        this.oleic = oleic;
    }

    public String getOleic_min() {
        return oleic_min;
    }

    public void setOleic_min(String oleic_min) {
        this.oleic_min = oleic_min;
    }

    public String getOleic_max() {
        return oleic_max;
    }

    public void setOleic_max(String oleic_max) {
        this.oleic_max = oleic_max;
    }

    public CompareHelper getPalmitic() {
        return palmitic;
    }

    public void setPalmitic(CompareHelper palmitic) {
        this.palmitic = palmitic;
    }

    public String getPalmitic_min() {
        return palmitic_min;
    }

    public void setPalmitic_min(String palmitic_min) {
        this.palmitic_min = palmitic_min;
    }

    public String getPalmitic_max() {
        return palmitic_max;
    }

    public void setPalmitic_max(String palmitic_max) {
        this.palmitic_max = palmitic_max;
    }

    public CompareHelper getStearic() {
        return stearic;
    }

    public void setStearic(CompareHelper stearic) {
        this.stearic = stearic;
    }

    public String getStearic_min() {
        return stearic_min;
    }

    public void setStearic_min(String stearic_min) {
        this.stearic_min = stearic_min;
    }

    public String getStearic_max() {
        return stearic_max;
    }

    public void setStearic_max(String stearic_max) {
        this.stearic_max = stearic_max;
    }

    public CompareHelper getHeight() {
        return height;
    }

    public void setHeight(CompareHelper height) {
        this.height = height;
    }

    public String getHeight_min() {
        return height_min;
    }

    public void setHeight_min(String height_min) {
        this.height_min = height_min;
    }

    public String getHeight_max() {
        return height_max;
    }

    public void setHeight_max(String height_max) {
        this.height_max = height_max;
    }

    public String getFlowerColor() {
        return flowerColor;
    }

    public void setFlowerColor(String flowerColor) {
        this.flowerColor = flowerColor;
    }

    public String getHilumColor() {
        return hilumColor;
    }

    public void setHilumColor(String hilumColor) {
        this.hilumColor = hilumColor;
    }

    public String getPodColor() {
        return podColor;
    }

    public void setPodColor(String podColor) {
        this.podColor = podColor;
    }

    public String getPubescenceColor() {
        return pubescenceColor;
    }

    public void setPubescenceColor(String pubescenceColor) {
        this.pubescenceColor = pubescenceColor;
    }

    public String getSeedCoatColor() {
        return seedCoatColor;
    }

    public void setSeedCoatColor(String seedCoatColor) {
        this.seedCoatColor = seedCoatColor;
    }

    public String getCotyledonColor() {
        return cotyledonColor;
    }

    public void setCotyledonColor(String cotyledonColor) {
        this.cotyledonColor = cotyledonColor;
    }

    public CompareHelper getWeightPer100seeds() {
        return weightPer100seeds;
    }

    public void setWeightPer100seeds(CompareHelper weightPer100seeds) {
        this.weightPer100seeds = weightPer100seeds;
    }

    public String getWeightPer100seeds_min() {
        return weightPer100seeds_min;
    }

    public void setWeightPer100seeds_min(String weightPer100seeds_min) {
        this.weightPer100seeds_min = weightPer100seeds_min;
    }

    public String getWeightPer100seeds_max() {
        return weightPer100seeds_max;
    }

    public void setWeightPer100seeds_max(String weightPer100seeds_max) {
        this.weightPer100seeds_max = weightPer100seeds_max;
    }

    public CompareHelper getUpperLeafletLength() {
        return upperLeafletLength;
    }

    public void setUpperLeafletLength(CompareHelper upperLeafletLength) {
        this.upperLeafletLength = upperLeafletLength;
    }

    public String getUpperLeafletLength_min() {
        return upperLeafletLength_min;
    }

    public void setUpperLeafletLength_min(String upperLeafletLength_min) {
        this.upperLeafletLength_min = upperLeafletLength_min;
    }

    public String getUpperLeafletLength_max() {
        return upperLeafletLength_max;
    }

    public void setUpperLeafletLength_max(String upperLeafletLength_max) {
        this.upperLeafletLength_max = upperLeafletLength_max;
    }

    public String getFloweringDate() {
        return floweringDate;
    }

    public void setFloweringDate(String floweringDate) {
        this.floweringDate = floweringDate;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public CompareHelper getYield() {
        return yield;
    }

    public void setYield(CompareHelper yield) {
        this.yield = yield;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getYield_min() {
        return yield_min;
    }

    public void setYield_min(String yield_min) {
        this.yield_min = yield_min;
    }

    public String getYield_max() {
        return yield_max;
    }

    public void setYield_max(String yield_max) {
        this.yield_max = yield_max;
    }
}
