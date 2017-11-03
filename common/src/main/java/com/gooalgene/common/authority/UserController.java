package com.gooalgene.common.authority;

import com.github.pagehelper.PageInfo;
import com.gooalgene.common.constant.ResultEnum;
import com.gooalgene.common.service.UserService;
import com.gooalgene.common.vo.ResultVO;
import com.gooalgene.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/manager")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public ResultVO findAll(@RequestParam(value = "pageNum",defaultValue = "0",required = false) Integer pageNum,
                                  @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize){
        PageInfo<User> users=userService.queryByPage(pageNum,pageSize);
        return ResultUtil.success(users);
    }

    @RequestMapping(value = "/change/enable",method = RequestMethod.POST)
    public ResultVO changeEnable(@RequestParam("id") String id){
        if(userService.enableUser(Integer.valueOf(id))){
            return ResultUtil.success();
        }
        return ResultUtil.error(ResultEnum.ENABLE_FAILED);
    }
}
