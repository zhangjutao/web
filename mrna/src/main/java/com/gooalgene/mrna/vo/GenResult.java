package com.gooalgene.mrna.vo;

import java.util.List;

public class GenResult {
	
	private List<GResultVo> cate;
	
	private String [] gens;

	private int gensTotal;

	public int getGensTotal() {
		return gensTotal;
	}

	public void setGensTotal(int gensTotal) {
		this.gensTotal = gensTotal;
	}

	public String[] getGens() {
		return gens;
	}

	public void setGens(String[] gens) {
		this.gens = gens;
	}

	public List<GResultVo> getCate() {
		return cate;
	}

	public void setCate(List<GResultVo> cate) {
		this.cate = cate;
	}

}
