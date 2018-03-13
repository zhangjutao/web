package com.gooalgene.entity;

import com.gooalgene.common.DataEntity;
import com.google.common.base.Objects;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by ShiYun on 2017/7/6 0006.
 */

public class Qtl extends DataEntity<Qtl> {

    public static final String NUM_TYPE_MORE = "MORE";  //大于
    public static final String NUM_TYPE_LESS = "LESS";  //小于
    public static final String NUM_TYPE_EQUAL = "EQUAL";  //等于


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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Qtl qtl = (Qtl) o;
        return Objects.equal(qtlName, qtl.qtlName) &&
                Objects.equal(trait, qtl.trait) &&
                Objects.equal(type, qtl.type) &&
                Objects.equal(chrlgId, qtl.chrlgId) &&
                Objects.equal(chr, qtl.chr) &&
                Objects.equal(lg, qtl.lg) &&
                Objects.equal(marker1, qtl.marker1) &&
                Objects.equal(marker2, qtl.marker2) &&
                Objects.equal(marker, qtl.marker) &&
                Objects.equal(associatedGenesId, qtl.associatedGenesId) &&
                Objects.equal(version, qtl.version) &&
                Objects.equal(method, qtl.method) &&
                Objects.equal(genomeStart, qtl.genomeStart) &&
                Objects.equal(genomeStartType, qtl.genomeStartType) &&
                Objects.equal(genomeEnd, qtl.genomeEnd) &&
                Objects.equal(genomeEndType, qtl.genomeEndType) &&
                Objects.equal(lod, qtl.lod) &&
                Objects.equal(lodType, qtl.lodType) &&
                Objects.equal(parent1, qtl.parent1) &&
                Objects.equal(parent2, qtl.parent2) &&
                Objects.equal(parent, qtl.parent) &&
                Objects.equal(ref, qtl.ref) &&
                Objects.equal(author, qtl.author) &&
                Objects.equal(keywords, qtl.keywords) &&
                Objects.equal(createtime, qtl.createtime);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(qtlName, trait, type, chrlgId, chr, lg, marker1, marker2, marker,
                associatedGenesId, version, method, genomeStart, genomeStartType, genomeEnd, genomeEndType, lod,
                lodType, parent1, parent2, parent, ref, author, keywords, createtime);
    }
}
