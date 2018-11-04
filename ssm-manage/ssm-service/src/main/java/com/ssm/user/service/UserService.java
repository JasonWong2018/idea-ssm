package com.ssm.user.service;

import com.ssm.common.utils.PageUtils;
import com.ssm.user.domain.SysUser;

import java.util.List;

public interface UserService {

    SysUser findUserByUsername(String username);

    List<SysUser> findSysUserList(PageUtils pageUtils, SysUser sysUser);
}
