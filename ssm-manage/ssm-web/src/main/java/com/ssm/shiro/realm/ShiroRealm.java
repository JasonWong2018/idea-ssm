package com.ssm.shiro.realm;

import com.ssm.role.domain.RoleInfo;
import com.ssm.role.service.RoleService;
import com.ssm.user.domain.User;
import com.ssm.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShiroRealm extends AuthorizingRealm {

    // 设置realm的名称
    @Override
    public void setName(String name) {
        super.setName("shiroRealm");
    }

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    // 用于授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();

        User user = userService.findUserByName(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //为用户授权,只需将用户的权限添加到info即可
        List<RoleInfo> roles = roleService.getRoleList(user);
        if(roles != null){
            for (RoleInfo role : roles) {
                authorizationInfo.addStringPermission(role.getRoleName());
            }
            return authorizationInfo;
        }
        return null;
    }

    //用于用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 通过表单接收的用户名
        String username  = token.getUsername();
        if(StringUtils.isNoneBlank(username)){
               User user = userService.findUserByName(username);
                                             if(user != null){
                return new SimpleAuthenticationInfo(user.getName(), user.getPassword(), getName());
            }
        }
        return null;
    }

    //清除缓存
    public void clearCached() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }

    //上传测试
}
