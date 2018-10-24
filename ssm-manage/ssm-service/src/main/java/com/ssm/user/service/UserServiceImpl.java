package com.ssm.user.service;

import com.ssm.user.domain.User;
import com.ssm.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserById(int id) {
        User user = userMapper.getUserById(id);
        return user;
    }

    public User findUserByName(String userName) {
        return userMapper.findUserByName(userName);
    }
}
