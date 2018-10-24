package com.ssm.role.service;

import com.ssm.role.domain.RoleInfo;
import com.ssm.user.domain.User;

import java.util.List;

public interface RoleService {
    List<RoleInfo> getRoleList(User user);
}
