package com.gooalgene.dna.entity;

import com.gooalgene.common.DataEntity;
import net.sf.json.JSONObject;

/**
 * 自定义群组　存储实体类
 * Created by ShiYun on 2017/9/18.
 */
public class DNAGroups extends DataEntity<DNAGroups> {

    private String name;

    private String condition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", getId());
        jsonObject.put("name", name);
        jsonObject.put("condition", condition.toString().replaceAll("\"", "\\\\\""));
        return jsonObject;
    }

    @Override
    public String toString() {
        return "DNAGroups{" +
                "name='" + name + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
