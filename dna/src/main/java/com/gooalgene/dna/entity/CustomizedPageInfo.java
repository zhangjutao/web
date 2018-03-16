package com.gooalgene.dna.entity;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class CustomizedPageInfo<T> extends PageInfo<T> {

    private List<T> data;

    public CustomizedPageInfo() {
    }

    public CustomizedPageInfo(List<T> list) {
        super(list);
    }

    public CustomizedPageInfo(List<T> list, int navigatePages) {
        super(list, navigatePages);
    }

    public List<T> getData() {
        return super.getList();
    }
}
