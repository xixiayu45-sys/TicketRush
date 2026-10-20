package com.ticketrush.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ticketrush.entity.User;
import com.ticketrush.exception.BusinessException;
import com.ticketrush.mapper.UserMapper;
import com.ticketrush.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String username, String password) {
        Long count = userMapper.selectCount(new QueryWrapper<User>().eq("username",username));
        if(count>0){
            throw new BusinessException(400,"用户已存在");
        }

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.insert(user);
        return user;
    }
}
