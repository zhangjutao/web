package com.gooalgene.entity;


import com.gooalgene.common.DataEntity;

import javax.persistence.Column;

/**
 * Created by ShiYun on 2017/7/5 0005.
 */

public class Marker extends DataEntity<Marker> {

    /**
     * Marker名
     */
    @Column(name = "marker_name")
    private String markerName;

    /**
     * Marker类型
     */
    @Column(name = "marker_type")
    private String markerType;

    /**
     * 连锁群编号，括号内为染色体编号
     */
    @Column(name = "marker_lg")
    private String markerLg;

    /**
     * Marker的遗传距离
     */
    private Float position;

    /**
     * Marker序列信息（可能有缺省）
     */
    @Column(name = "amplification_info")
    private String amplificationInfo;

    /**
     * Marker信息提供者（可能有缺省）
     */
    private String provider;

    /**
     * 参考文献（可能有缺省）
     */
    private String ref;

    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    public String getMarkerType() {
        return markerType;
    }

    public void setMarkerType(String markerType) {
        this.markerType = markerType;
    }

    public String getMarkerLg() {
        return markerLg;
    }

    public void setMarkerLg(String markerLg) {
        this.markerLg = markerLg;
    }

    public Float getPosition() {
        return position;
    }

    public void setPosition(Float position) {
        this.position = position;
    }

    public String getAmplificationInfo() {
        return amplificationInfo;
    }

    public void setAmplificationInfo(String amplificationInfo) {
        this.amplificationInfo = amplificationInfo;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
