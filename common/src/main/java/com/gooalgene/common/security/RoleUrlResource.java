package com.gooalgene.common.security;

/**
 * @author Crabime
 * 资源文件路径与用户之间的映射关系bean
 */
public class RoleUrlResource {

    private String url;
    private String role;

    public RoleUrlResource(String url, String role) {
        this.url = url;
        this.role = role;
    }

    public String getUrl() {
        return url;
    }

    public String getRole() {
        return role;
    }
}
