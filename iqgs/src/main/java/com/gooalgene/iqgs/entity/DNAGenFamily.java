package com.gooalgene.iqgs.entity;

import com.gooalgene.common.DataEntity;

public class DNAGenFamily extends DataEntity<DNAGenFamily> {
    private String familyId;
    private String treeJson;

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public String getTreeJson() {
		return treeJson;
	}

	public void setTreeJson(String treeJson) {
		this.treeJson = treeJson;
	}

}
