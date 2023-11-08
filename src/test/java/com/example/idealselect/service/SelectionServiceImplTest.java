package com.example.idealselect.service;

import com.example.idealselect.entity.IdealSelection;
import com.example.idealselect.entity.User;
import com.example.idealselect.repository.IdealSelectionMapper;
import com.example.idealselect.repository.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.UUID;


@Slf4j
@Transactional
@SpringBootTest
class SelectionServiceImplTest {



    @Autowired
    private IdealSelectionMapper selectionMapper;
    @Autowired
    private UserMapper userMapper;
    private final static String TITLE = "TITLE";
    private final static String BODY = "BODY";
    private static User user;

    @BeforeAll
    static void config(){
        user = User.builder().id(1L).userId("USERID").userName("USERNAME").password("PASSWORD").build();

    }

    @Test
    void saveTest(){
        userMapper.save(user);
        IdealSelection selection = IdealSelection.builder().title(TITLE).body(BODY).creator(user).subCount(0).filePath(UUID.randomUUID().toString()).updateTime(LocalDateTime.now()).build();
        selectionMapper.save(selection);

        IdealSelection findSelection = selectionMapper.findById(selection.getId()).get();
        log.info("findSelection={}", findSelection);

    }

}