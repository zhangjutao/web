package com.gooalgene.mrna.entity;

/**
 * Created by Administrator on 2017/08/05.
 */
public class SampleRun {

    private String name;

    private String type;

    private String study;

    private Double value;

    public SampleRun(String name, String type, String study, Double value) {
        this.name = name;
        this.type = type;
        this.study = study;
        this.value = value;
    }

    public SampleRun() {
    }

    @Override
    public String toString() {
        return "SampleRun{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", study='" + study + '\'' +
                ", value=" + value +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
