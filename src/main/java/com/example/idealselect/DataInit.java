package com.example.idealselect;

import com.example.idealselect.entity.Ideal;
import com.example.idealselect.entity.IdealSelection;
import com.example.idealselect.entity.User;
import com.example.idealselect.repository.IdealMapper;
import com.example.idealselect.repository.IdealSelectionMapper;
import com.example.idealselect.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
public class DataInit {

    private final UserMapper userMapper;
    private final IdealMapper idealMapper;
    private final IdealSelectionMapper selectionMapper;

    @Value("${file:}")
    private String rootPath;
    private final List<String> selectionPath = List.of("온라인 게임 월드컵", "롤 챔피언 월드컵", "역대 축구선수 공격수 월드컵", "서구권 여배우 이상형 월드컵",
            "컵라면 월드컵", "한국 드라마 월드컵", "새끼 동물 월드컵", "최애 포켓몬 월드컵", "여자 아이돌 이상형 월드컵",
            "최강 동물 월드컵", "최애 음식 월드컵");

    public DataInit(UserMapper userMapper, IdealMapper idealMapper, IdealSelectionMapper selectionMapper) {
        this.userMapper = userMapper;
        this.idealMapper = idealMapper;
        this.selectionMapper = selectionMapper;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("Initial Data Init");

        Optional<User> initialUser = userMapper.findByUserId("InitialUser");
        if (initialUser.isEmpty()) {
            createUser();
        }

        for(int i = 0; i < selectionPath.size(); i++){
            String path = selectionPath.get(i);
            Optional<IdealSelection> selection = selectionMapper.findByFilePath(path);
            if(selection.isEmpty()){
                IdealSelection createdSelection = createSelection(initialUser.get(), path);
                createIdeals(path, createdSelection);
            }
        }

    }

    @Transactional
    public void createUser() {
        User user = User.builder().userId("InitialUser").userName("InitialUser").password("1234").build();
        userMapper.save(user);
    }

    @Transactional
    public void createIdeals(String selectionPath1, IdealSelection selection) {
        Path path = Paths.get(rootPath, selectionPath1).toAbsolutePath();
        List<Ideal> idealList = new ArrayList<>();
        try {
            Files.list(path)
                    .filter(Files::isRegularFile)
                    .forEach(p -> idealList.add(Ideal.builder().idealName(p.getFileName().toString())
                            .selectionId(selection.getId()).build()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        idealMapper.saveAll(idealList);
    }

    @Transactional
    public IdealSelection createSelection(User initialUser, String path) {
        Random random = new Random();
        IdealSelection selection = IdealSelection.builder().creator(initialUser).title(path).body("23/11/20 업데이트")
                .filePath(path).updateTime(LocalDateTime.now()).build();
        selectionMapper.save(selection);
        return selection;
    }
}


