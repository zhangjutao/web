package com.gooalgene.entity;


import com.gooalgene.common.DataEntity;

/**
 * Created by ShiYun on 2017/7/6 0006.
 */
public class Soybean extends DataEntity<Soybean> {

    private String categoryName;

    private String category;

    private String listName;

    private String qtlName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getQtlName() {
        return qtlName;
    }

    public void setQtlName(String qtlName) {
        this.qtlName = qtlName;
    }
}
