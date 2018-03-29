package com.gooalgene.dna.entity;

/**
 * 用户选择的group中包含的一组dna_run记录，该记录中major、minor在结果SNP中占的比例
 * 如group为保存地方的群体在dna_run中查到50条记录，然后根据区间搜索查找到200条SNP，找出SNP中
 * 966个sample中50个run_no，计算50个run_no中0/0，0/1，1/1出现的比例
 *
 * @author crabime
 */
public class SampleFrequency {

    /**
     * 群体名称
     */
    private String name;

    /**
     * 群体中样本的数目
     */
    private int size;

    /**
     * 该群体在SNP中major的比例
     */
    private Double major;

    /**
     * 该群体在SNP中minor的比例
     */
    private Double minor;

    public SampleFrequency() {
    }

    public SampleFrequency(String name, int size, Double major, Double minor) {
        this.name = name;
        this.size = size;
        this.major = major;
        this.minor = minor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Double getMajor() {
        return major;
    }

    public void setMajor(Double major) {
        this.major = major;
    }

    public Double getMinor() {
        return minor;
    }

    public void setMinor(Double minor) {
        this.minor = minor;
    }
}
