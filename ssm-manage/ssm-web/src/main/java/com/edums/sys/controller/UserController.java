package com.edums.sys.controller;

import com.edums.common.base.BaseController;
import com.edums.common.cons.Constant;
import com.edums.common.utils.CookieUtils;
import com.edums.common.utils.MD5Encrypt;
import com.edums.common.utils.PageUtils;
import com.edums.sys.domain.SysUser;
import com.edums.sys.sevice.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    private final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/userList")
    public String userList(@ModelAttribute("pageUtils") PageUtils pageUtils,@ModelAttribute("sysUser") SysUser sysUser ,HttpServletRequest request){
        try {
            List<SysUser> users = userService.findSysUserList(pageUtils,sysUser);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return "page/user/user_list";
    }


    @RequestMapping("/index")
    public String index(HttpServletRequest request){
        return "index";
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(SysUser sysUser, HttpServletRequest request, HttpServletResponse response){
        try {
            if(StringUtils.isBlank(sysUser.getUsername())){
                return "账户不能为空";
            }
            if(StringUtils.isBlank(sysUser.getPasswd())){
                return "请输入密码";
            }
            String validCode = request.getParameter("validCode");
            if(StringUtils.isEmpty(validCode)){
                return "验证码不能为空";
            }
            String code = (String) request.getSession().getAttribute("code");
            if(!validCode.equalsIgnoreCase(code)){
                return "验证码错误";
            }
            SysUser user = userService.findUserByUsername(sysUser.getUsername().trim());
            if(null == user){
                return "账户错误";
            }
            if(!user.getPasswd().equalsIgnoreCase(MD5Encrypt.md5(sysUser.getPasswd()))){
                return "密码错误";
            }

            String online = request.getParameter("online") == null ?"":request.getParameter("online");
            // 设定COOKIE值
            if ("is".equals(online)) {// 记住密码自动登录
                CookieUtils.setCookie(response, user,Constant.COOKIE_EXPIRY);
            } else {
                CookieUtils.clearCookieValue(response);
            }
            return SUCCESS;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return "系统异常";
        }
    }
}
