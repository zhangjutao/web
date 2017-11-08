package com.gooalgene.common.authority;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority{
    private String name;
    private String description;
    private Integer id;

    public Role(String name) {
        this.name = name;
    }
    public Role() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
