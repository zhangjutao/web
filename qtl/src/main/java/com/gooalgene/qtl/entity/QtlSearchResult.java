package com.gooalgene.qtl.entity;

/**
 * QTL搜索结果
 */
public class QtlSearchResult {
    private int id;
    private String qtlName;
    private String trait;
    private String type;
    private String marker1;
    private String marker2;
    private String method;
    private String lod;
    private String parent1;
    private String parent2;
    private String ref;
    private String author;
    private String geneStart;
    private String geneEnd;
    private String markerlg;
    private int genesNum;
    private String chr;
    private String lg;
    private int associateGeneId;
    private String associateGenes;
    private String version;

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

    public int getAssociateGeneId() {
        return associateGeneId;
    }

    public void setAssociateGeneId(int associateGeneId) {
        this.associateGeneId = associateGeneId;
    }

    public String getAssociateGenes() {
        return associateGenes;
    }

    public void setAssociateGenes(String associateGenes) {
        this.associateGenes = associateGenes;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
