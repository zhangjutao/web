package com.gooalgene.qtl.entity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.json.JsonArray;

public class QtlTableEntity {
    String type;
    String keywords;
    JSONObject condition;
    int pageNo;
    int pageSize;
    JSONArray chrs;
    JSONArray lgs;
    int total;

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }

    JSONArray data;

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

    public JSONObject getCondition() {
        return condition;
    }

    public void setCondition(JSONObject condition) {
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

    public JSONArray getChrs() {
        return chrs;
    }

    public void setChrs(JSONArray chrs) {
        this.chrs = chrs;
    }

    public JSONArray getLgs() {
        return lgs;
    }

    public void setLgs(JSONArray lgs) {
        this.lgs = lgs;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
