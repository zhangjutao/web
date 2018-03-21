package com.gooalgene.entity;

import java.sql.Timestamp;
import java.util.List;

/**
 * mrna部分：study表
 * <p/>
 * Created by ShiYun on 2017/8/2 0002.
 */
public class Study{
    private String sampleRun;
    private String sampleName;
    private Integer isExpression;
    private String sraStudy;
    private String study;
    private String tissueForClassification;
    private String tissue;
    private String preservation;
    private String treat;
    private String stage;
    private String geneType;
    private String phenoType;
    private String environment;
    private String geoloc;
    private String ecotype;
    private String collectionDate;
    private String coordinates;
    private String type;
    private String scientificName;
    private String pedigree;
    private String reference;
    private String institution;
    private String submissionTime;
    private String instrument;
    private String libraryStrategy;
    private String librarySource;
    private String libraryLayout;
    private String insertSize;
    private String readlength;
    private Integer spots;
    private String experiment;
    private String links;
    private Integer id;
    private java.sql.Timestamp createtime;
    private String keywords;
    private List<String> tissues;
    private String tissueKeywords;

    public List<String> getTissues() {
        return tissues;
    }

    public void setTissues(List<String> tissues) {
        this.tissues = tissues;
    }

    public String getTissueKeywords() {
        return tissueKeywords;
    }

    public void setTissueKeywords(String tissueKeywords) {
        this.tissueKeywords = tissueKeywords;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSampleRun() {
        return sampleRun;
    }

    public void setSampleRun(String sampleRun) {
        this.sampleRun = sampleRun;
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

    public String getTissueForClassification() {
        return tissueForClassification;
    }

    public void setTissueForClassification(String tissueForClassification) {
        this.tissueForClassification = tissueForClassification;
    }

    public String getTissue() {
        return tissue;
    }

    public void setTissue(String tissue) {
        this.tissue = tissue;
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

    public String getGeoloc() {
        return geoloc;
    }

    public void setGeoloc(String geoloc) {
        this.geoloc = geoloc;
    }

    public String getEcotype() {
        return ecotype;
    }

    public void setEcotype(String ecotype) {
        this.ecotype = ecotype;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getReadlength() {
        return readlength;
    }

    public void setReadlength(String readlength) {
        this.readlength = readlength;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }
}
