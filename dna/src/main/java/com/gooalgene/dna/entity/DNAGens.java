package com.gooalgene.dna.entity;

import com.gooalgene.common.DataEntity;
import net.sf.json.JSONObject;

/**
 * DNA 搜索基因实体类
 *
 * @author Crabime
 */
public class DNAGens extends DataEntity<DNAGens> {

    private String geneId;
    private String geneName;
    private String description;

    private Long start;
    private Long end;

    private String chromosome;

    private String keywords; //当all查询时才存在

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
