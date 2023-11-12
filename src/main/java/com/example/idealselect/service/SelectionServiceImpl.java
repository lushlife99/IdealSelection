package com.example.idealselect.service;

import com.example.idealselect.dto.IdealSelectionDto;
import com.example.idealselect.entity.Ideal;
import com.example.idealselect.entity.IdealSelection;
import com.example.idealselect.entity.User;
import com.example.idealselect.exception.CustomException;
import com.example.idealselect.exception.ErrorCode;
import com.example.idealselect.repository.IdealMapper;
import com.example.idealselect.repository.IdealSelectionMapper;
import com.example.idealselect.repository.SelectionSearchCond;
import com.example.idealselect.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.spi.DirectoryManager;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SelectionServiceImpl implements IdealSelectionService{

    private final IdealSelectionMapper selectionMapper;
    private final IdealMapper idealMapper;
    private final SessionManager sessionManager;
    @Value("${file:}")
    private String rootFilePath;

    @Override
    public List<IdealSelectionDto> getByPopularity(Integer pageNum, HttpServletRequest request) {
        List<IdealSelection> selectionList = selectionMapper.findAllBySearchCond(new SelectionSearchCond("", "POPULARITY"), 10 * pageNum);
        List<IdealSelectionDto> dtoList = new ArrayList<>();
        for (IdealSelection selection : selectionList) {
            dtoList.add(new IdealSelectionDto(selection));
        }

        return dtoList;
    }

    @Override
    public List<IdealSelectionDto> getByLatest(HttpServletRequest request) {
        return null;
    }

    @Transactional
    @Override
    public IdealSelectionDto create(String title, String body, List<MultipartFile> files, HttpServletRequest request){
        User user = sessionManager.getSession(request).orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER));
        IdealSelection selection = IdealSelection.builder()
                .title(title).body(body).creator(user).build();

        selectionMapper.save(selection);
        Path path = Paths.get(rootFilePath, selection.getFilePath());
        List<Ideal> idealList = new ArrayList<>();

        try {
            Files.createDirectories(path);
            for (MultipartFile file : files) {
                String name = file.getOriginalFilename();
                Path imagePath = Paths.get(path.toString(), name);
                file.transferTo(new File(imagePath.toString()));
                Ideal ideal = Ideal.builder().idealName(name).winRate(0.0).winCount(0).selection(selection).build();
                idealList.add(ideal);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        idealMapper.saveAll(idealList);
        return null;
    }

    @Override
    public IdealSelectionDto update(IdealSelectionDto idealSelection, HttpServletRequest request) {
        return null;
    }

    @Override
    public void delete(Long id, HttpServletRequest request) {

    }

    @Override
    public IdealSelectionDto getRanking(HttpServletRequest request) {
        return null;
    }

    @Override
    public void updateWinRate(Long idealSelectionId, Long winIdealId, Long defeatIdealId, HttpServletRequest request) {

    }

    @Override
    public void updateWinCount(Long idealSelectionId, Long idealId, HttpServletRequest request) {

    }

    @Override
    public void updateSubCount(Long idealSelectionId, HttpServletRequest request) {

    }

    @Override
    public List<IdealSelectionDto> getCreationList(int pagePrefix, HttpServletRequest request) {
        User user = sessionManager.getSession(request).get();
        List<IdealSelection> selectionList = selectionMapper.findPageableByCreatorId(user.getId(), 10 * pagePrefix);

        List<IdealSelectionDto> dtoList = new ArrayList<>();
        for (IdealSelection selection : selectionList) {
            dtoList.add(new IdealSelectionDto(selection));
        }

        return dtoList;
    }

    public byte[] getIdealImg(String imageName, String imageName2) throws IOException {

        Path imagePath = Paths.get(rootFilePath, imageName, imageName2);
        Resource imageResource = new UrlResource(imagePath.toUri());
        if (imageResource.exists()) {
            return Files.readAllBytes(imagePath);
        } else {
            throw new CustomException(ErrorCode.FILE_NOT_FOUND);
        }

    }
}
