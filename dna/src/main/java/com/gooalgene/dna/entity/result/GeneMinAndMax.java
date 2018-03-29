package com.gooalgene.dna.entity.result;

/**
 * 某个染色体区间上基因长度的最小值、最大值
 */
public class GeneMinAndMax {

    /**
     * 区间内基因位置最小值
     */
    private Long min;

    /**
     * 区间内基因位置最大值
     */
    private Long max;

    /**
     * 染色体名称
     */
    private String chromosome;

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }
}
