package com.example.idealselect.service;

import com.example.idealselect.entity.User;
import com.example.idealselect.repository.UserMapper;
import groovy.util.logging.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserMapper userMapper;
    private static final String USERID = "user01";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "1234";

    private static MockHttpServletRequest request;

    @BeforeAll
    static void init(){
        request = new MockHttpServletRequest();
        /**
         * 나중에 세션 넣어서 테스트.
         */
    }

    @Test
    void saveTest(){
        User user = User.builder().userId(USERID).userName(USERNAME).password(PASSWORD).build();

        userMapper.save(user);
        User findUser = userMapper.findByUserId(USERID).get();

        assertThat(findUser.getUserId()).isEqualTo(USERID);
    }

    @Test
    void updateTest(){
        User user = User.builder().userId(USERID).userName(USERNAME).password(PASSWORD).build();
        userMapper.save(user);
        User findUser = userMapper.findByUserId(USERID).get();
        final String updatePassword = "Pw Is Changed";

        findUser.setPassword(updatePassword);

        userMapper.update(findUser.getId(), findUser);

        User updateUser = userMapper.findById(findUser.getId()).get();
        assertThat(updateUser.getPassword()).isEqualTo(updatePassword);
    }

    @Test
    void deleteTest(){
        User user = User.builder().userId(USERID).userName(USERNAME).password(PASSWORD).build();
        userMapper.save(user);
        User findUser = userMapper.findByUserId(USERID).get();

        userMapper.deleteById(findUser.getId());

        assertThat(userMapper.findById(findUser.getId()).isEmpty()).isTrue();
    }

    @Test
    void selectTest(){
        User user = User.builder().userId(USERID).userName(USERNAME).password(PASSWORD).build();
        userMapper.save(user);

        User findUser = userMapper.findByUserId(USERID).get();

        assertThat(findUser.getUserId()).isEqualTo(USERID);
    }


    @Test
    void join() {

    }

    @Test
    void login() {
    }

    @Test
    void logOut() {
    }




}