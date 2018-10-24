package com.ssm.role.mapper;

import com.ssm.role.domain.RoleInfo;
import com.ssm.user.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("roleMapper")
public interface RoleMapper {
    List<RoleInfo> getRoleList(User user);
}
