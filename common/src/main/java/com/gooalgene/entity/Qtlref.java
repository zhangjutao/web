package com.gooalgene.entity;


import com.gooalgene.common.DataEntity;

import javax.persistence.Column;

/**
 * Created by ShiYun on 2017/7/5 0005.
 */

public class Qtlref extends DataEntity<Qtlref> {


    /**
     * 文章标题名
     */
    private String title;

    /**
     * 作者
     */
    private String authors;

    /**
     * 所发表的杂志信息
     */
    private String source;

    /**
     * 文章摘要（abstract为java关键字不能用）
     */
    @Column(name = "abstract")
    private String summary;

    /**
     * 链接
     */
    private String pubmed;

    /**
     * qtl_name
     */
    @Column(name = "qtl_name")
    private String qtlName;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPubmed() {
        return pubmed;
    }

    public void setPubmed(String pubmed) {
        this.pubmed = pubmed;
    }

    public String getQtlName() {
        return qtlName;
    }

    public void setQtlName(String qtlName) {
        this.qtlName = qtlName;
    }

    @Override
    public String toString() {
        return "Qtlref{" +
                "title='" + title + '\'' +
                ", authors='" + authors + '\'' +
                ", source='" + source + '\'' +
                ", summary='" + summary + '\'' +
                ", pubmed='" + pubmed + '\'' +
                ", qtlName='" + qtlName + '\'' +
                '}';
    }
}
