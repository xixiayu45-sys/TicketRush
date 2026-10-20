package com.ticketrush.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ticketrush.entity.User;
import com.ticketrush.exception.BusinessException;
import com.ticketrush.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    void registerShouldStoreHashedPassword() {
        User user = userService.register("alice", "secret123");

        assertNotNull(user.getId(), "注册后应该回填自增 id");

        User saved = userMapper.selectOne(new QueryWrapper<User>().eq("username", "alice"));
        assertNotNull(saved);
        assertNotEquals("secret123", saved.getPasswordHash(), "数据库里不能存明文密码");
        assertTrue(saved.getPasswordHash().startsWith("$2"), "应该是 BCrypt 哈希值");
    }

    @Test
    void registerShouldRejectDuplicateUsername() {
        userService.register("bob", "secret123");

        BusinessException ex = assertThrows(BusinessException.class,
                () -> userService.register("bob", "another123"));

        assertEquals(400, ex.getCode().intValue());
    }
}
