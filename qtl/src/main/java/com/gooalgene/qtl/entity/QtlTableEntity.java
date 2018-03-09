package com.gooalgene.qtl.entity;

import net.sf.json.JSONObject;

import java.util.List;

public class QtlTableEntity {
    private String type;
    private String keywords;
    private String condition;
    private int pageNo;
    private int pageSize;
    private List<String> chrs;
    private List<String> lgs;
    private List<QtlSearchResult> data;
    private int total;

    public List<QtlSearchResult> getData() {
        return data;
    }

    public void setData(List<QtlSearchResult> data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getLgs() {
        return lgs;
    }

    public void setLgs(List<String> lgs) {
        this.lgs = lgs;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<String> getChrs() {
        return chrs;
    }

    public void setChrs(List<String> chrs) {
        this.chrs = chrs;
    }
}
