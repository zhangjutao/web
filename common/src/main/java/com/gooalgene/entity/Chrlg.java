package com.gooalgene.entity;

import com.gooalgene.common.DataEntity;

import javax.persistence.Column;

/**
 * 染色体信息表实体类
 * Created by ShiYun on 2017/7/6 0006.
 */
public class Chrlg extends DataEntity<Chrlg> {

    /**
     * 染色体编号
     */
    private String chr;

    /**
     * 连锁群编号
     */
    private String lg;

    /**
     * 基因版本不同，长度不同
     */
    private String version;

    /**
     * 物理距离总长度（碱基个数）
     */
    private Integer bps;
    /**
     * 遗传距离总长度（cMs）
     */
    private Float cMs;

    /**
     * 每单位遗传距离（cM）的碱基个数，用bps/cMs计算的来的，可以去掉。
     */
    @Column(name = "cm_bp")
    private Integer cmBp;

    public String getChr() {
        return chr;
    }

    public void setChr(String chr) {
        this.chr = chr;
    }

    public String getLg() {
        return lg;
    }

    public void setLg(String lg) {
        this.lg = lg;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getBps() {
        return bps;
    }

    public void setBps(Integer bps) {
        this.bps = bps;
    }

    public Float getcMs() {
        return cMs;
    }

    public void setcMs(Float cMs) {
        this.cMs = cMs;
    }

    public Integer getCmBp() {
        return cmBp;
    }

    public void setCmBp(Integer cmBp) {
        this.cmBp = cmBp;
    }

    @Override
    public String toString() {
        return "Chrlg{id='" + getId() + '\'' +
                ",chr='" + chr + '\'' +
                ", lg='" + lg + '\'' +
                ", bps=" + bps +
                ", cMs=" + cMs +
                ", version=" + version +
                ", cmBp=" + cmBp +
                '}';
    }
}
