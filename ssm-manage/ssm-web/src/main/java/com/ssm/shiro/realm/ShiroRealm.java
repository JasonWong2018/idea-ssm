package com.ssm.shiro.realm;

import com.ssm.user.domain.SysUser;
import com.ssm.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroRealm extends AuthorizingRealm {

    // 设置realm的名称
    @Override
    public void setName(String name) {
        super.setName("shiroRealm");
    }

    @Autowired
    private UserService userService;


    // 用于授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();

        SysUser sysUser = userService.findUserByUsername(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //为用户授权,只需将用户的权限添加到info即可
       // List<RoleInfo> roles = roleService.getRoleList(sysUser);
//        if(roles != null){
//            for (RoleInfo role : roles) {
//                authorizationInfo.addStringPermission(role.getRoleName());
//            }
//            return authorizationInfo;
//        }
        return null;
    }

    //用于用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 通过表单接收的用户名
        String username  = token.getUsername();
        if(StringUtils.isNoneBlank(username)){
               SysUser sysUser = userService.findUserByUsername(username);
                                             if(sysUser != null){
                return new SimpleAuthenticationInfo(sysUser.getUsername(), sysUser.getPasswd(), getName());
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
