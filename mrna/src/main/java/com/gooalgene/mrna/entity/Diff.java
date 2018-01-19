package com.gooalgene.mrna.entity;

/**
 * Created by ShiYun on 2017/8/4 0004.
 */
public class Diff {

    private String name;

    private Double log_value;

    private Double p_value;

    private Double q_value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLog_value() {
        return log_value;
    }

    public void setLog_value(Double log_value) {
        this.log_value = log_value;
    }

    public Double getP_value() {
        return p_value;
    }

    public void setP_value(Double p_value) {
        this.p_value = p_value;
    }

    public Double getQ_value() {
        return q_value;
    }

    public void setQ_value(Double q_value) {
        this.q_value = q_value;
    }

    public Diff(String name, Double log_value, Double p_value, Double q_value) {
        this.name = name;
        this.log_value = log_value;
        this.p_value = p_value;
        this.q_value = q_value;
    }

    public Diff() {
    }

    @Override
    public String toString() {
        return "Diff{" +
                "name='" + name + '\'' +
                ", log_value=" + log_value +
                ", p_value=" + p_value +
                ", q_value=" + q_value +
                '}';
    }
}
