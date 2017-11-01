package com.gooalgene.entity;

import com.gooalgene.common.DataEntity;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by ShiYun on 2017/7/6 0006.
 */

public class Qtl extends DataEntity<Qtl> {

    public static final String NUM_TYPE_MORE = "MORE";//大于
    public static final String NUM_TYPE_LESS = "LESS";//小于
    public static final String NUM_TYPE_EQUAL = "EQUAL";//等于


    /**
     * qtl名字，唯一检索
     */
    private String qtlName;

    /**
     * 所属trait
     */
    private String trait;

    /**
     * 所属类别
     */
    private String type;

    /**
     * 对应染色体
     */
    private Integer chrlgId;

    private String chr;

    private String lg;

    private String marker1;

    private String marker2;

    private String marker;

    private Integer associatedGenesId;

    private String version;

    private String method;

    private Float genomeStart;

    private String genomeStartType;

    private Float genomeEnd;

    private String genomeEndType;

    private Float lod;

    private String lodType;

    private String parent1;

    private String parent2;

    private String parent;

    private String ref;

    private String author;

    private String keywords;//当all查询时才存在

    @Column(name = "createtime", columnDefinition = "timestamp")
    private Date createtime;

    public String getQtlName() {
        return qtlName;
    }

    public void setQtlName(String qtlName) {
        this.qtlName = qtlName;
    }

    public String getTrait() {
        return trait;
    }

    public void setTrait(String trait) {
        this.trait = trait;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getChrlgId() {
        return chrlgId;
    }

    public void setChrlgId(Integer chrlgId) {
        this.chrlgId = chrlgId;
    }

    public String getMarker1() {
        return marker1;
    }

    public void setMarker1(String marker1) {
        this.marker1 = marker1;
    }

    public String getMarker2() {
        return marker2;
    }

    public void setMarker2(String marker2) {
        this.marker2 = marker2;
    }

    public Integer getAssociatedGenesId() {
        return associatedGenesId;
    }

    public void setAssociatedGenesId(Integer associatedGenesId) {
        this.associatedGenesId = associatedGenesId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Float getGenomeStart() {
        return genomeStart;
    }

    public void setGenomeStart(Float genomeStart) {
        this.genomeStart = genomeStart;
    }

    public Float getGenomeEnd() {
        return genomeEnd;
    }

    public void setGenomeEnd(Float genomeEnd) {
        this.genomeEnd = genomeEnd;
    }

    public Float getLod() {
        return lod;
    }

    public void setLod(Float lod) {
        this.lod = lod;
    }

    public String getParent1() {
        return parent1;
    }

    public void setParent1(String parent1) {
        this.parent1 = parent1;
    }

    public String getParent2() {
        return parent2;
    }

    public void setParent2(String parent2) {
        this.parent2 = parent2;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getChr() {
        return chr;
    }

    public void setChr(String chr) {
        this.chr = chr;
    }

    public String getLg() {
        return lg;
    }

    public void setLg(String lg) {
        this.lg = lg;
    }

    public String getGenomeStartType() {
        return genomeStartType;
    }

    public void setGenomeStartType(String genomeStartType) {
        this.genomeStartType = genomeStartType;
    }

    public String getGenomeEndType() {
        return genomeEndType;
    }

    public void setGenomeEndType(String genomeEndType) {
        this.genomeEndType = genomeEndType;
    }

    public String getLodType() {
        return lodType;
    }

    public void setLodType(String lodType) {
        this.lodType = lodType;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Qtl{" +
                "qtlName='" + qtlName + '\'' +
                ", trait='" + trait + '\'' +
                ", type='" + type + '\'' +
                ", chrlgId=" + chrlgId +
                ", chr='" + chr + '\'' +
                ", lg='" + lg + '\'' +
                ", marker1='" + marker1 + '\'' +
                ", marker2='" + marker2 + '\'' +
                ", associatedGenesId=" + associatedGenesId +
                ", version='" + version + '\'' +
                ", method='" + method + '\'' +
                ", genomeStart=" + genomeStart +
                ", genomeStartType='" + genomeStartType + '\'' +
                ", genomeEnd=" + genomeEnd +
                ", genomeEndType='" + genomeEndType + '\'' +
                ", lod=" + lod +
                ", lodType='" + lodType + '\'' +
                ", parent1='" + parent1 + '\'' +
                ", parent2='" + parent2 + '\'' +
                ", ref='" + ref + '\'' +
                ", author='" + author + '\'' +
                ", createtime=" + createtime +
                '}';
    }
}
