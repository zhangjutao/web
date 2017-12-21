package com.gooalgene.common.constant;

public enum ResultEnum {
    UNKONW_ERROR(-1, "系统异常"),
    ENABLE_FAILED(-2,"审核用户失败"),
    EMAIL_REPEAT(-3,"邮箱重复"),
    SNP_ID_NOT_EXIST(-4,"snpId不存在"),

    SEQUENCE_ERROR(-99,"sequence输入错误"),
    SUCCESS(0, "成功"),
    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}