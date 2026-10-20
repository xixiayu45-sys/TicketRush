package com.ticketrush.service;

import com.ticketrush.entity.User;

public interface UserService {
    User register(String username,String password);
}
