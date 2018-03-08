package com.gooalgene.qtl.entity;

public class AsObjectForQtlTableEntity {
    int id;
    String qtlName;
    String trait;
    String type;
    String marker1;
    String marker2;
    String method;
    String lod;
    String parent1;
    String parent2;
    String ref;
    String author;
    String geneStart;
    String geneEnd;
    String markerlg;
    int genesNum;
    String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getGenesNum() {
        return genesNum;
    }

    public void setGenesNum(int genesNum) {
        this.genesNum = genesNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getLod() {
        return lod;
    }

    public void setLod(String lod) {
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

    public String getGeneStart() {
        return geneStart;
    }

    public void setGeneStart(String geneStart) {
        this.geneStart = geneStart;
    }

    public String getGeneEnd() {
        return geneEnd;
    }

    public void setGeneEnd(String geneEnd) {
        this.geneEnd = geneEnd;
    }

    public String getMarkerlg() {
        return markerlg;
    }

    public void setMarkerlg(String markerlg) {
        this.markerlg = markerlg;
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

    public String getAssociateGenes() {
        return associateGenes;
    }

    public void setAssociateGenes(String associateGenes) {
        this.associateGenes = associateGenes;
    }

    String chr;
    String lg;
    String associateGenes;
}
