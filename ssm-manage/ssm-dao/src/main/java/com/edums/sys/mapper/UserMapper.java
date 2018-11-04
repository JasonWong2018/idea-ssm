package com.edums.sys.mapper;

import com.github.abel533.mapper.Mapper;
import com.edums.sys.domain.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("userMapper")
public interface UserMapper  extends Mapper<SysUser> {

    List<Map<String, Object>> findSysUserList(SysUser sysUser);
}
