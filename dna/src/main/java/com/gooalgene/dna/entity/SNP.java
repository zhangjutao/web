package com.gooalgene.dna.entity;

import net.sf.json.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Map;

/**
 * Created by ShiYun on 2017/9/11 0011.
 */
@Document
public class SNP {

    @Id
    private String id;//snp ID,以"GlyS0"+"染色体编号"+"0"+"染色体位置"命名

    private String chr;//染色体或scaffold名称

    private Long pos;//SNP在染色体或scaffold的位置

    private String ref;//参考碱基类型

    private String alt;//变异碱基类型

    private Double qual;//位点质量值（过滤用，不展示）

    private Double maf;//最小等位基因频率（过滤用，不展示）

    private Integer lenght;//lenght是指indel片段的长度 intel数据有，snp无

    private String type;//位点未在区域类型（intergenic间区，intronic内含子，exonic外显子，upstream基因上游，downstream基因下游，UTR5 5'端UTR等）

    private String gene;//
    private String genecontent;//位点注释到的基因相关信息

    private String effect;//同义突变/非同义突变/stopgain(停止或延长转录突变)

    private String consequencetype;//type_effect

    private Map<String, String> samples;//对应的样本数据

    private String majorallen;
    private String minorallen;

    private Double major;
    private Double minor;

    public Double getMinor() {
        return minor;
    }

    public void setMinor(Double minor) {
        this.minor = minor;
    }

    public String getChr() {
        return chr;
    }

    public void setChr(String chr) {
        this.chr = chr;
    }

    public Long getPos() {
        return pos;
    }

    public void setPos(Long pos) {
        this.pos = pos;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Double getQual() {
        return qual;
    }

    public void setQual(Double qual) {
        this.qual = qual;
    }

    public Double getMaf() {
        return maf;
    }

    public void setMaf(Double maf) {
        this.maf = maf;
    }

    public Integer getLenght() {
        return lenght;
    }

    public void setLenght(Integer lenght) {
        this.lenght = lenght;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGene() {
        return gene;
    }

    public void setGene(String gene) {
        this.gene = gene;
    }

    public String getGenecontent() {
        return genecontent;
    }

    public void setGenecontent(String genecontent) {
        this.genecontent = genecontent;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getConsequencetype() {
        return consequencetype;
    }

    public void setConsequencetype(String consequencetype) {
        this.consequencetype = consequencetype;
    }

    public Map<String, String> getSamples() {
        return samples;
    }

    public void setSamples(Map<String, String> samples) {
        this.samples = samples;
    }

    public String getMinorallen() {
        return minorallen;
    }

    public void setMinorallen(String minorallen) {
        this.minorallen = minorallen;
    }

    public String getMajorallen() {
        return majorallen;
    }

    public void setMajorallen(String majorallen) {
        this.majorallen = majorallen;
    }

    public Double getMajor() {
        return major;
    }

    public void setMajor(Double major) {
        this.major = major;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("chr", chr);
        jsonObject.put("pos", pos);
        jsonObject.put("ref", ref);
        jsonObject.put("alt", alt);
        jsonObject.put("qual", qual);
        jsonObject.put("maf", maf);
        jsonObject.put("lenght", lenght);
        jsonObject.put("type", type);
        jsonObject.put("gene", genecontent);
        jsonObject.put("effect", effect);
        jsonObject.put("consequencetype", consequencetype);
        jsonObject.put("majorallen", majorallen);
        jsonObject.put("minorallen", minorallen);
        jsonObject.put("major", major);
        jsonObject.put("minor", minor);
        return jsonObject;
    }

    @Override
    public String toString() {
        return "SNP{" +
                "id='" + id + '\'' +
                ", chr='" + chr + '\'' +
                ", pos=" + pos +
                ", ref='" + ref + '\'' +
                ", alt='" + alt + '\'' +
                ", qual=" + qual +
                ", maf=" + maf +
                ", lenght=" + lenght +
                ", type='" + type + '\'' +
                ", gene='" + gene + '\'' +
                ", genecontent='" + genecontent + '\'' +
                ", effect='" + effect + '\'' +
                ", consequencetype='" + consequencetype + '\'' +
                ", majorallen='" + majorallen + '\'' +
                ", minorallen='" + minorallen + '\'' +
                ", major=" + major +
                ", minor=" + minor +
                '}';
    }
}
