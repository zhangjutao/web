package com.gooalgene.iqgs.entity.sort;

public class SortedResult {
    private String geneId;

    private String geneName;

    private String description;

    private String chromosome;

    private String location;

    private Double score;

    public SortedResult(String geneId, String geneName, String description, String chromosome, String location, Double score) {
        this.geneId = geneId;
        this.geneName = geneName;
        this.description = description;
        this.chromosome = chromosome;
        this.location = location;
        this.score = score;
    }

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
