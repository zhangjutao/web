package com.gooalgene.common.authority;

/**
 * Created by liuyan on 2017/11/1.
 */
public class User_Role {
    private int user_id;
    private int role_id;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User_Role(int user_id, int role_id){
        this.user_id=user_id;
        this.role_id=role_id;
    }
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
