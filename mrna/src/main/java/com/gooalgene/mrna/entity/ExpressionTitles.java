package com.gooalgene.mrna.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

/**
 * 此表存储表达基因的表头数据，因为单独查询耗时较长
 * Created by ShiYun on 2017/8/31 0031.
 */
@Document(collection = "ExpressionTitles")
public class ExpressionTitles {
    @Id
    private String id;

    private String sraStudy;

    private List<String> titles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public String getSraStudy() {
        return sraStudy;
    }

    public void setSraStudy(String sraStudy) {
        this.sraStudy = sraStudy;
    }
}
