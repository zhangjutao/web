package com.gooalgene.entity;

import com.gooalgene.common.DataEntity;

/**
 * Created by ShiYun on 2017/8/28 0028.
 */
public class IndexExplain extends DataEntity<IndexExplain> {

    private String type;

    private String detail;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
