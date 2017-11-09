package com.gooalgene.iqgs.entity;

import com.gooalgene.common.DataEntity;

public class DNAGenFamilyRel extends DataEntity<DNAGenFamilyRel> {
	private String geneId;
	private String familyId;

	public String getGeneId() {
		return geneId;
	}

	public void setGeneId(String geneId) {
		this.geneId = geneId;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

}
