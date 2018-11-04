package com.edums.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * shiro框架登录
 */
@Controller
@RequestMapping("/shiro")
public class LoginController {
    @RequestMapping(value = "/login", method = RequestMethod.POST,produces = "text/html;charset=utf-8")
    @ResponseBody
    public String doLogin(String name,String password) {

        Subject subject = SecurityUtils.getSubject();
        // 把用户名和密码封装为UsernamePasswordToken 对象
        UsernamePasswordToken token = new UsernamePasswordToken(name,password);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
           return "用户名或密码错误";
        }
        if (subject.isAuthenticated()) {
            return "redirect:/";
        }
        return "redirect:/login";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(){
        return "SUCCESS";
    }
}
