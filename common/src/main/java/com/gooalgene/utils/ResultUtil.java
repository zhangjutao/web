package com.gooalgene.utils;

import com.gooalgene.common.constant.ResultEnum;
import com.gooalgene.common.vo.ResultVO;

public class ResultUtil {
    public static ResultVO success(Object object){
        ResultVO ResultVO = new ResultVO();
        ResultVO.setCode(0);
        ResultVO.setMsg("成功");
        ResultVO.setData(object);
        return ResultVO;
    }

    public static ResultVO success(){
        ResultVO ResultVO = new ResultVO();
        ResultVO.setCode(0);
        ResultVO.setMsg("成功");
        ResultVO.setData(null);
        return ResultVO;
    }

    public static ResultVO error(ResultEnum resultEnum){
        ResultVO ResultVO = new ResultVO();
        ResultVO.setCode(resultEnum.getCode());
        ResultVO.setMsg(resultEnum.getMsg());
        ResultVO.setData(null);
        return ResultVO;
    }

    public static ResultVO error(Integer code,String msg){
        ResultVO ResultVO = new ResultVO();
        ResultVO.setCode(code);
        ResultVO.setMsg(msg);
        ResultVO.setData(null);
        return ResultVO;
    }
}