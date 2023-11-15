package com.example.idealselect.service;

import com.example.idealselect.entity.Ideal;
import com.example.idealselect.entity.IdealSelection;
import com.example.idealselect.entity.User;
import com.example.idealselect.repository.IdealMapper;
import com.example.idealselect.repository.IdealSelectionMapper;
import com.example.idealselect.repository.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Transactional
@Slf4j
@SpringBootTest
class IdealServiceImplTest {

    @Autowired
    private IdealMapper idealMapper;
    @Autowired
    private IdealSelectionMapper selectionMapper;
    @Autowired
    private UserMapper userMapper;
    private static User user;
    private static IdealSelection selection;

    @BeforeAll
    static void config(){
        user = User.builder().userId("aaaa").password("1234").userName("chan").build();

        selection = IdealSelection.builder()
                .title("title").body("body").filePath(UUID.randomUUID().toString()).idealList(new ArrayList<>()).subCount(0)
                .creator(user).updateTime(LocalDateTime.now()).build();
    }

    @Test
    void saveAllTest(){
        userMapper.save(user);
        selectionMapper.save(selection);
        List<Ideal> idealList= new ArrayList<>();

        for(int i = 0; i < 256; i++)  //256개 ideal 생성
            idealList.add(Ideal.builder().idealName("Name").selectionId(selection.getId()).winCount(0).build());

        log.info("start save iterator. currentTime={}", LocalDateTime.now());
        for (Ideal ideal : idealList) {
            idealMapper.save(ideal);
        }
        log.info("end save iterator. currentTime={}", LocalDateTime.now());

        log.info("start batch save. currentTime={}", LocalDateTime.now());
        idealMapper.saveAll(idealList);
        log.info("end batch save. currentTime={}", LocalDateTime.now());
    }
}