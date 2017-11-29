package com.gooalgene.common.handler;

import com.gooalgene.common.exception.DnaException;
import com.gooalgene.common.vo.ResultVO;
import com.gooalgene.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ExceptionHandle {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO handler(Exception e){
        if(e instanceof DnaException){
            DnaException dnaException = (DnaException)e;
            return ResultUtil.error(dnaException.getCode(), dnaException.getMessage());
        } else {
            logger.error("【系统错误】", e);
            return ResultUtil.error(-1, "系统异常");
        }
    }
}
