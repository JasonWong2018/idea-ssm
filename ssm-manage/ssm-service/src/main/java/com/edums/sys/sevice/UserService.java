package com.edums.sys.sevice;

import com.edums.common.utils.PageUtils;
import com.edums.sys.domain.SysUser;

import java.util.List;

public interface UserService {

    SysUser findUserByUsername(String username);

    List<SysUser> findSysUserList(PageUtils pageUtils, SysUser sysUser);
}
