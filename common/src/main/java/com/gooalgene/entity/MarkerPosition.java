package com.gooalgene.entity;

import com.gooalgene.common.DataEntity;

import javax.persistence.Column;

/**
 * Created by ShiYun on 2017/7/10 0010.
 */
public class MarkerPosition extends DataEntity<MarkerPosition> {

    /**
     * Marker名
     */
    @Column(name = "marker_name")
    private String markerName;


    /**
     * 连锁群编号，括号内为染色体编号
     */
    @Column(name = "chr")
    private String chr;

    /**
     * Marker类型
     */
    @Column(name = "marker_type")
    private String markerType;


    /**
     * Marker比对信息版本
     */
    private String version;

    /**
     * marker序列在染色体上的起始位置
     */
    private Integer startPos;

    /**
     * marker序列在染色体上的终止位置
     */
    private Integer endPos;

    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    public String getChr() {
        return chr;
    }

    public void setChr(String chr) {
        this.chr = chr;
    }

    public String getMarkerType() {
        return markerType;
    }

    public void setMarkerType(String markerType) {
        this.markerType = markerType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getStartPos() {
        return startPos;
    }

    public void setStartPos(Integer startPos) {
        this.startPos = startPos;
    }

    public Integer getEndPos() {
        return endPos;
    }

    public void setEndPos(Integer endPos) {
        this.endPos = endPos;
    }
}
