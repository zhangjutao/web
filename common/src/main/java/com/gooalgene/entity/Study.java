package com.gooalgene.entity;

import com.gooalgene.common.DataEntity;

import java.util.Date;
import java.util.List;

/**
 * mrna部分：study表
 * <p/>
 * Created by ShiYun on 2017/8/2 0002.
 */
public class Study extends DataEntity<Study> {

    private String sraStudy;//研究课题信息检索号
    private String study;//研究课题
    private String sampleName;//样本名称
    private Integer isExpression; //是否表达（或者差异） 0:表达 1：差异
    private String sampleRun;//测序数据检索号
    private String tissue;//组织
    private String tissueForClassification;//组织分类
    private String preservation;
    private String treat; //处理方式
    private String stage; //生长阶段
    private String geneType;//基因型
    private String phenoType;//表型
    private String environment;//生长环境
    private String geoLoc;
    private String ecoType;
    private String collectionDate;
    private String coordinates;
    private String ccultivar;//品种
    private String scientificName;//拉丁文名称
    private String pedigree;
    private String reference;//参考文献
    private String institution;//研究机构
    private String submissionTime;
    private String instrument;
    private String libraryStrategy;
    private String librarySource;
    private String libraryLayout;//建库方式
    private String insertSize;
    private String readLength;
    private Integer spots;
    private String experiment;//实验信息检索号
    private String links;//链接

    private Date createTime;//入库时间

    private String keywords;//当为all时传入关键字
    private String tissueKeywords;//搜索框模糊匹配，侧边栏精确匹配

    private List<String> tissues;

    public String getSraStudy() {
        return sraStudy;
    }

    public void setSraStudy(String sraStudy) {
        this.sraStudy = sraStudy;
    }

    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public Integer getIsExpression() {
        return isExpression;
    }

    public void setIsExpression(Integer isExpression) {
        this.isExpression = isExpression;
    }

    public String getSampleRun() {
        return sampleRun;
    }

    public void setSampleRun(String sampleRun) {
        this.sampleRun = sampleRun;
    }

    public String getTissue() {
        return tissue;
    }

    public void setTissue(String tissue) {
        this.tissue = tissue;
    }

    public String getTissueForClassification() {
        return tissueForClassification;
    }

    public void setTissueForClassification(String tissueForClassification) {
        this.tissueForClassification = tissueForClassification;
    }

    public String getPreservation() {
        return preservation;
    }

    public void setPreservation(String preservation) {
        this.preservation = preservation;
    }

    public String getTreat() {
        return treat;
    }

    public void setTreat(String treat) {
        this.treat = treat;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getGeneType() {
        return geneType;
    }

    public void setGeneType(String geneType) {
        this.geneType = geneType;
    }

    public String getPhenoType() {
        return phenoType;
    }

    public void setPhenoType(String phenoType) {
        this.phenoType = phenoType;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getGeoLoc() {
        return geoLoc;
    }

    public void setGeoLoc(String geoLoc) {
        this.geoLoc = geoLoc;
    }

    public String getEcoType() {
        return ecoType;
    }

    public void setEcoType(String ecoType) {
        this.ecoType = ecoType;
    }

    public String getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate = collectionDate;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getCcultivar() {
        return ccultivar;
    }

    public void setCcultivar(String ccultivar) {
        this.ccultivar = ccultivar;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getPedigree() {
        return pedigree;
    }

    public void setPedigree(String pedigree) {
        this.pedigree = pedigree;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(String submissionTime) {
        this.submissionTime = submissionTime;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getLibraryStrategy() {
        return libraryStrategy;
    }

    public void setLibraryStrategy(String libraryStrategy) {
        this.libraryStrategy = libraryStrategy;
    }

    public String getLibrarySource() {
        return librarySource;
    }

    public void setLibrarySource(String librarySource) {
        this.librarySource = librarySource;
    }

    public String getLibraryLayout() {
        return libraryLayout;
    }

    public void setLibraryLayout(String libraryLayout) {
        this.libraryLayout = libraryLayout;
    }

    public String getInsertSize() {
        return insertSize;
    }

    public void setInsertSize(String insertSize) {
        this.insertSize = insertSize;
    }

    public String getReadLength() {
        return readLength;
    }

    public void setReadLength(String readLength) {
        this.readLength = readLength;
    }

    public Integer getSpots() {
        return spots;
    }

    public void setSpots(Integer spots) {
        this.spots = spots;
    }

    public String getExperiment() {
        return experiment;
    }

    public void setExperiment(String experiment) {
        this.experiment = experiment;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getTissueKeywords() {
        return tissueKeywords;
    }

    public void setTissueKeywords(String tissueKeywords) {
        this.tissueKeywords = tissueKeywords;
    }

    public List<String> getTissues() {
        return tissues;
    }

    public void setTissues(List<String> tissues) {
        this.tissues = tissues;
    }

    @Override
    public String toString() {
        return "Study{" +
                "sraStudy='" + sraStudy + '\'' +
                ", study='" + study + '\'' +
                ", sampleName='" + sampleName + '\'' +
                ", isExpression=" + isExpression +
                ", sampleRun='" + sampleRun + '\'' +
                ", tissue='" + tissue + '\'' +
                ", tissueForClassification='" + tissueForClassification + '\'' +
                ", preservation='" + preservation + '\'' +
                ", treat='" + treat + '\'' +
                ", stage='" + stage + '\'' +
                ", geneType='" + geneType + '\'' +
                ", phenoType='" + phenoType + '\'' +
                ", environment='" + environment + '\'' +
                ", geoLoc='" + geoLoc + '\'' +
                ", ecoType='" + ecoType + '\'' +
                ", collectionDate='" + collectionDate + '\'' +
                ", coordinates='" + coordinates + '\'' +
                ", ccultivar='" + ccultivar + '\'' +
                ", scientificName='" + scientificName + '\'' +
                ", pedigree='" + pedigree + '\'' +
                ", reference='" + reference + '\'' +
                ", institution='" + institution + '\'' +
                ", submissionTime='" + submissionTime + '\'' +
                ", instrument='" + instrument + '\'' +
                ", libraryStrategy='" + libraryStrategy + '\'' +
                ", librarySource='" + librarySource + '\'' +
                ", libraryLayout='" + libraryLayout + '\'' +
                ", insertSize='" + insertSize + '\'' +
                ", readLength='" + readLength + '\'' +
                ", spots=" + spots +
                ", experiment='" + experiment + '\'' +
                ", links='" + links + '\'' +
                ", createTime=" + createTime +
                ", keywords='" + keywords + '\'' +
                ", tissueKeywords='" + tissueKeywords + '\'' +
                ", tissues=" + tissues +
                '}';
    }
}
