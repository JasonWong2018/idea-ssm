package com.ssm.user.service;

import com.ssm.common.base.BaseService;
import com.ssm.common.utils.PageUtils;
import com.ssm.user.domain.SysUser;
import com.ssm.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    private UserMapper userMapper;

    public SysUser findUserByUsername(String username) {
        SysUser user = new SysUser();
        user.setUsername(username);
        SysUser sysUser = userMapper.selectOne(user);
        return sysUser;
    }

    public List<SysUser> findSysUserList(PageUtils pageUtils, SysUser sysUser) {
        super.startPage(pageUtils);
        List<Map<String, Object>> dataList = userMapper.findSysUserList(sysUser);
        super.setPageUtils(pageUtils,dataList);
        return null;
    }
}
