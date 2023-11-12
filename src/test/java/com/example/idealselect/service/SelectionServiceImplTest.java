package com.example.idealselect.service;

import com.example.idealselect.entity.IdealSelection;
import com.example.idealselect.entity.User;
import com.example.idealselect.repository.IdealSelectionMapper;
import com.example.idealselect.repository.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @Test
    void pageTest() {
        userMapper.save(user);

        List<IdealSelection> idealSelectionList = new ArrayList<>();
        for(int i = 0; i < 11; i++){
            IdealSelection selection = IdealSelection.builder().title(TITLE).body(BODY).creator(user).subCount(0).filePath(UUID.randomUUID().toString()).updateTime(LocalDateTime.now()).build();
            idealSelectionList.add(selection);
            selectionMapper.save(selection);
        }


        List<IdealSelection> searchList1 = selectionMapper.findPageableByCreatorId(user.getId(), 0);
        List<IdealSelection> searchList2 = selectionMapper.findPageableByCreatorId(user.getId(), 10);


        Assertions.assertThat(searchList1.size()).isEqualTo(10);
        Assertions.assertThat(searchList2.size()).isEqualTo(1);

    }

}