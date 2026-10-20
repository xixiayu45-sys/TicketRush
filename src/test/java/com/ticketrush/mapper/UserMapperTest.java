package com.ticketrush.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ticketrush.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void insertAndSelectByUsername() {
        User user = new User();
        user.setUsername("tester");
        user.setPasswordHash("fake-hash-for-test");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userMapper.insert(user);

        assertNotNull(user.getId(), "插入后应该回填自增 id");

        User found = userMapper.selectOne(
                new QueryWrapper<User>().eq("username", "tester"));

        assertNotNull(found, "应该能按用户名查到刚插入的用户");
        assertEquals("tester", found.getUsername());
        assertEquals("fake-hash-for-test", found.getPasswordHash());
    }
}
