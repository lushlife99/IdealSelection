package com.example.idealselect;

import com.example.idealselect.entity.User;
import com.example.idealselect.repository.IdealMapper;
import com.example.idealselect.repository.IdealSelectionMapper;
import com.example.idealselect.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class DataInit {

    private final UserMapper userMapper;
    private final IdealMapper idealMapper;
    private final IdealSelectionMapper selectionMapper;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");

        User user = User.builder().userId("test").userName("test").password("!234").build();
        userMapper.save(user);

        //root 디렉토리 확인하고 생성하기!!
    }
}
