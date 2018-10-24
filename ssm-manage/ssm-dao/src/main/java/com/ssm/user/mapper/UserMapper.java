package com.ssm.user.mapper;

import com.ssm.user.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("userMapper")
public interface UserMapper {
    User getUserById(@Param("id") int id);

    User findUserByName(@Param("name") String name);
}
