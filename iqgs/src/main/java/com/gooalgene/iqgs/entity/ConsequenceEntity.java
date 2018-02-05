package com.gooalgene.iqgs.entity;

/**
 * Tomcat启动时缓存的ConsequenceType及ID实体bean
 * @author Crabime
 */
public class ConsequenceEntity {

    private String type;

    private String consequenceType;

    private int id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConsequenceType() {
        return consequenceType;
    }

    public void setConsequenceType(String consequenceType) {
        this.consequenceType = consequenceType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
