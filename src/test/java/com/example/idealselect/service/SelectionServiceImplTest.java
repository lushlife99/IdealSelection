package com.example.idealselect.service;

import com.example.idealselect.dto.IdealSelectionDto;
import com.example.idealselect.entity.Ideal;
import com.example.idealselect.entity.IdealSelection;
import com.example.idealselect.entity.User;
import com.example.idealselect.repository.IdealMapper;
import com.example.idealselect.repository.IdealSelectionMapper;
import com.example.idealselect.repository.UserMapper;
import com.example.idealselect.session.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@RequiredArgsConstructor
@Transactional
@SpringBootTest
class SelectionServiceImplTest {

    @Autowired
    private IdealSelectionService selectionService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IdealMapper idealMapper;
    @Autowired
    private IdealSelectionMapper selectionMapper;
    @Autowired
    private SessionManager sessionManager;
    private MockHttpServletRequest request = new MockHttpServletRequest();
    private MockHttpServletResponse response = new MockHttpServletResponse();
    private final User user = User.builder().userId("test").userName("test").password("1234").build();
    private static final String TITLE = "TestTitle";
    private static final String BODY = "BODY";
    private static final String FILEPATH = "TestPath";
    @Value("${file:}")
    private String rootFilePath;

    @BeforeEach
    void init(){
        userMapper.save(user);
        sessionManager.createSession(user, response);
        Cookie cookie = response.getCookie(SessionManager.SESSION_COOKIE_NAME);
        request.setCookies(cookie);
        Random random = new Random();

        for(int i = 0; i < 110; i++) {
            IdealSelection selection = IdealSelection.builder().title(TITLE).body(BODY).updateTime(LocalDateTime.now().minusMinutes(random.nextLong(60))).creator(user).subCount(random.nextInt(Integer.MAX_VALUE))
                    .idealList(new ArrayList<>()).replyList(new ArrayList<>()).filePath(FILEPATH).build();
            selectionMapper.save(selection);
        }

    }

    @Test
    @DisplayName("이상형 월드컵 인기순 정렬 테스트")
    void orderByPopular() {

        List<IdealSelectionDto> selectionList1 = selectionService.getByPopularity(TITLE, 0, request);
        List<IdealSelectionDto> selectionList2 = selectionService.getByPopularity(TITLE, 10, request);

        List<Integer> popularityList1 = extractProperty("subCount", Integer.class).from(selectionList1);
        List<Integer> popularityList2 = extractProperty("subCount", Integer.class).from(selectionList2);

        assertThat(popularityList1).isSortedAccordingTo(Comparator.reverseOrder());
        assertThat(popularityList2).isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test
    @DisplayName("이상형 월드컵 페이징 테스트")
    void getSelectionByPaging(){
        List<IdealSelectionDto> selectionList1 = selectionService.getCreationList(0, request);
        List<IdealSelectionDto> selectionList2 = selectionService.getCreationList( 10, request);

        assertThat(selectionList1.size()).isEqualTo(100);
        assertThat(selectionList2.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("이상형 월드컵 최신순 정렬 테스트")
    void getByLatest() {
        List<IdealSelectionDto> selectionList1 = selectionService.getByLatest(TITLE, 0, request);
        List<IdealSelectionDto> selectionList2 = selectionService.getByLatest(TITLE, 10, request);
        List<IdealSelectionDto> selectionList = Stream.concat(selectionList1.stream(), selectionList2.stream()).collect(Collectors.toList());

        List<LocalDateTime> latestList = extractProperty("updateTime", LocalDateTime.class).from(selectionList);

        assertThat(latestList).isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test
    @DisplayName("이상형 월드컵 생성 테스트")
    void create() throws IOException {
        List<MultipartFile> files = new ArrayList<>();
        MockMultipartFile file1 = new MockMultipartFile("file", "test1.jpg", "image/jpeg", new byte[1]);
        MockMultipartFile file2 = new MockMultipartFile("file", "test2.jpg", "image/jpeg", new byte[1]);
        files.add(file1);
        files.add(file2);

        IdealSelectionDto idealSelectionDto = selectionService.create("TITLE", "BODY", files, request);

        // Assertions
        IdealSelection findSelection = selectionMapper.findByIdAllResult(idealSelectionDto.getId()).get();
        List<Ideal> idealList = findSelection.getIdealList();
        Path path = Paths.get(rootFilePath, findSelection.getFilePath(), idealList.get(0).getIdealName());
        Path path2 = Paths.get(rootFilePath, findSelection.getFilePath(), idealList.get(1).getIdealName());
        File savedFile1 = new File(path.toString());
        File savedFile2 = new File(path2.toString());

        assertThat(findSelection.getTitle()).isEqualTo("TITLE");
        assertThat(findSelection.getBody()).isEqualTo("BODY");
        assertThat(findSelection.getCreator().getId()).isEqualTo(user.getId());
        assertThat(findSelection.getIdealList().size()).isEqualTo(2);
        assertThat(idealList.get(0).getIdealName()).isEqualTo("test1.jpg");
        assertThat(idealList.get(1).getIdealName()).isEqualTo("test2.jpg");
        assertThat(savedFile1).isNotEmpty();
        assertThat(savedFile2).isNotEmpty();

        /**
         * 생성된 파일 삭제
         */

        Files.deleteIfExists(path);
        Files.deleteIfExists(path2);
        Files.delete(Paths.get(rootFilePath, findSelection.getFilePath()));

    }


    @Test
    @DisplayName("이상형 월드컵 삭제 테스트")
    void delete() {
        IdealSelection selection = IdealSelection.builder().title(TITLE).body(BODY).creator(user).build();
        selectionMapper.save(selection);

        selectionService.delete(selection.getId(), request);

        assertThat(selectionMapper.findById(selection.getId()).isEmpty()).isTrue();
    }


    @Test
    void getCreationList() {
    }

    @Test
    void getIdealImg() {
    }

    @Test
    void getSelection() {
    }

    @Test
    void testGetSelection() {
    }

    @Test
    @DisplayName("플레이 할 이상형 월드컵의 총 라운드 수와 배열의 길이가 일치하는지 테스트")
    void playableSelectionSizeTest() {

        IdealSelection selection = IdealSelection.builder().title(TITLE).body(BODY).creator(user).build();
        selectionMapper.save(selection);
        int round = 8;
        List<Ideal> idealList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Ideal ideal = Ideal.builder().idealName(UUID.randomUUID().toString()).selectionId(selection.getId()).build();
            idealList.add(ideal);
        }
        idealMapper.saveAll(idealList);

        IdealSelectionDto playableSelection = selectionService.getPlayableSelection(selection.getId(), round, request);

        assertThat(playableSelection).isNotNull();
        assertThat(playableSelection.getIdealList()).hasSize(round);

    }

    @Test
    @DisplayName("플레이 할 이상형 리스트를 랜덤으로 가져오는지 테스트")
    void playableSelectionRandomTest() {

        IdealSelection selection = IdealSelection.builder().title(TITLE).body(BODY).creator(user).build();
        selectionMapper.save(selection);
        List<Ideal> idealList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Ideal ideal = Ideal.builder().idealName(UUID.randomUUID().toString()).selectionId(selection.getId()).build();
            idealList.add(ideal);
        }
        idealMapper.saveAll(idealList);

        List<IdealSelectionDto> selectionDtoList = new ArrayList<>();
        int roundsToTest = 5;
        int round = 8;
        for (int i = 0; i < roundsToTest; i++) {
            IdealSelectionDto playableSelection = selectionService.getPlayableSelection(selection.getId(), round, request);
            selectionDtoList.add(playableSelection);
        }

        List<List<Ideal>> idealLists = selectionDtoList.stream()
                .map(IdealSelectionDto::getIdealList)
                .collect(Collectors.toList());

        for (int i = 0; i < idealLists.size(); i++) {
            for (int j = i + 1; j < idealLists.size(); j++) {
                assertThat(idealLists.get(i)).isNotEqualTo(idealLists.get(j));
            }
        }
    }
}