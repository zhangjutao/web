package com.gooalgene.dna.entity;

public class ChromosomeList {
    private Integer id;
    /**
     * 染色体名字
     */
    private String chromosome;

    /**
     * 染色体最大长度
     */
    private Integer length;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
