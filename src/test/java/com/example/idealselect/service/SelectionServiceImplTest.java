package com.example.idealselect.service;

import com.example.idealselect.entity.IdealSelection;
import com.example.idealselect.entity.User;
import com.example.idealselect.repository.IdealSelectionMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.UUID;


@Slf4j
@SpringBootTest
class SelectionServiceImplTest {

    @Autowired
    private IdealSelectionService selectionService;

    @Autowired
    private IdealSelectionMapper selectionMapper;
    private final static String TITLE = "TITLE";
    private final static String BODY = "BODY";
    private static User user;

    @BeforeAll
    static void config(){
        user = User.builder().id(1L).userId("USERID").userName("USERNAME").password("PASSWORD").build();
    }

    @Test
    void saveTest(){
        IdealSelection selection = IdealSelection.builder().title(TITLE).body(BODY).creator(user).subCount(0).filePath(UUID.randomUUID().toString()).build();
        selectionMapper.save(selection);

        IdealSelection findSelection = selectionMapper.findById(selection.getId()).get();
        log.info("findSelection={}", findSelection);

    }

}