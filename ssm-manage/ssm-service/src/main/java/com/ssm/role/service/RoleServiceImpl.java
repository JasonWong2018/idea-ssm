package com.ssm.role.service;

import com.ssm.role.domain.RoleInfo;
import com.ssm.role.mapper.RoleMapper;
import com.ssm.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public List<RoleInfo> getRoleList(User user) {

        return roleMapper.getRoleList(user);
    }
}
