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

@Slf4j
public class DataInit {

    private final UserMapper userMapper;
    private final IdealMapper idealMapper;
    private final IdealSelectionMapper selectionMapper;

    @Value("${file:}")
    private String rootPath;
    private final String selectionPath1 = "InitialIdealSelection1";
    private final String selectionPath2 = "InitialIdealSelection2";
    private final String selectionPath3 = "InitialIdealSelection3";
    private final String selectionPath4 = "InitialIdealSelection4";

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

        Optional<IdealSelection> initialIdealSelection1 = selectionMapper.findByFilePath(selectionPath1);
        Optional<IdealSelection> initialIdealSelection2 = selectionMapper.findByFilePath(selectionPath2);
        Optional<IdealSelection> initialIdealSelection3 = selectionMapper.findByFilePath(selectionPath3);
        Optional<IdealSelection> initialIdealSelection4 = selectionMapper.findByFilePath(selectionPath4);

        if (initialIdealSelection1.isEmpty()) {
            IdealSelection selection = createSelection(initialUser, "여자 아이돌 이상형 월드컵", selectionPath1);
            createIdeals(selectionPath1, selection);
        }

        if (initialIdealSelection2.isEmpty()) {
            IdealSelection selection = createSelection(initialUser, "최강 동물 월드컵", selectionPath2);
            createIdeals(selectionPath2, selection);
        }

        if (initialIdealSelection3.isEmpty()) {
            IdealSelection selection = createSelection(initialUser, "최애 음식 월드컵", selectionPath3);
            createIdeals(selectionPath3, selection);
        }

        if (initialIdealSelection4.isEmpty()) {
            IdealSelection selection = createSelection(initialUser, "최애 포켓몬 월드컵", selectionPath4);
            createIdeals(selectionPath4, selection);
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
    public IdealSelection createSelection(Optional<User> initialUser, String title, String selectionPath1) {
        IdealSelection selection = IdealSelection.builder().creator(initialUser.get()).title(title).body("23/11/20 업데이트")
                .filePath(selectionPath1).updateTime(LocalDateTime.now()).build();
        selectionMapper.save(selection);
        return selection;
    }
}


