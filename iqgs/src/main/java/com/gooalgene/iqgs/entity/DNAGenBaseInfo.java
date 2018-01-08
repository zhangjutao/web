package com.gooalgene.iqgs.entity;

import com.gooalgene.common.Page;

/**
 * Created by sauldong on 2017/10/12.
 */
public class DNAGenBaseInfo {
    private String geneId;
    private String geneOldId;
    private String geneName;
    private String geneType;
    private String locus;
    private String length;
    private String species;
    private String functions;
    private String description;
	// 用于基因家族查询
	private String familyId;
	private Integer id;
    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getGeneType() {
        return geneType;
    }

    public void setGeneType(String geneType) {
        this.geneType = geneType;
    }

    public String getLocus() {
        return locus;
    }

    public void setLocus(String locus) {
        this.locus = locus;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getFunctions() {
        return functions;
    }

    public void setFunctions(String functions) {
        this.functions = functions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

    public String getGeneOldId() {
        return geneOldId;
    }

    public void setGeneOldId(String geneOldId) {
        this.geneOldId = geneOldId;
    }
}
