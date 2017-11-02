package com.gooalgene.common.authority;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.gooalgene.common.Page;
import com.gooalgene.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class UserController {
    @Autowired
    private UserService userService;

    /*@RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView users(){
        return new ModelAndView("/manager/manager");
    }*/

    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public Page<User> findAll(@RequestParam(value = "pageNo",defaultValue = "0") Integer pageNo,
                              @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        return userService.queryByPage(new Page(pageNo,pageSize));
    }
}
