package com.gooalgene.common.exception;

import com.gooalgene.common.constant.ResultEnum;

public class DnaException extends RuntimeException{

    private Integer code;
    private ResultEnum resultEnum;

    public DnaException( ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code=resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
