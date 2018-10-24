package com.ssm.user.controller;

import com.ssm.user.domain.User;
import com.ssm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    @ResponseBody
    public String index(){
        return "SUCCESS";
    }

    @RequestMapping("/getUserById")
    @ResponseBody
    public User getUserById(int id){
        User user = userService.getUserById(id);
        return user;
    }

}
