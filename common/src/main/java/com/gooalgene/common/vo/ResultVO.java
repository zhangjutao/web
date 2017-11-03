package com.gooalgene.common.vo;

public class ResultVO<T> {
    //返回只状态明细
    private String msg;
    //返回状态码
    private Integer code;
    //响应吗
    private Integer status;
    //返回数据
    private T data;

    public ResultVO(){

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
