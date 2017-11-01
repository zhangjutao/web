package com.gooalgene.entity;

import com.gooalgene.common.DataEntity;

/**
 * 系统配置类
 */
public class Configuration extends DataEntity<Configuration> {
    private String key;
    private String value;

    public Configuration() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
