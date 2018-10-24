package com.ssm.user.service;

import com.ssm.user.domain.User;

public interface UserService {
    User getUserById(int id);

    User findUserByName(String userName);
}
