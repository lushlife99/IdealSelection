package com.example.idealselect.service;

import com.example.idealselect.dto.IdealDto;
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
import org.springframework.beans.factory.xml.UtilNamespaceHandler;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SelectionServiceImpl implements IdealSelectionService{

    private final IdealSelectionMapper selectionMapper;
    private final IdealMapper idealMapper;
    private final SessionManager sessionManager;
    @Value("${file:}")
    private String rootFilePath;

    @Override
    public List<IdealSelectionDto> getByPopularity(String context, Integer pageNum, HttpServletRequest request) {
        List<IdealSelection> selectionList = selectionMapper.findAllBySearchCond(new SelectionSearchCond(context, "POPULARITY"), 10 * pageNum);
        List<IdealSelectionDto> dtoList = new ArrayList<>();
        for (IdealSelection selection : selectionList) {
            IdealSelectionDto idealSelectionDto = new IdealSelectionDto(selection);
            dtoList.add(idealSelectionDto);
        }

        return dtoList;
    }

    @Override
    public List<IdealSelectionDto> getByLatest(String context, Integer pageNum, HttpServletRequest request) {
        List<IdealSelection> selectionList = selectionMapper.findAllBySearchCond(new SelectionSearchCond(context, "LATEST"), 10 * pageNum);
        List<IdealSelectionDto> dtoList = new ArrayList<>();
        for (IdealSelection selection : selectionList) {
            IdealSelectionDto idealSelectionDto = new IdealSelectionDto(selection);
            dtoList.add(idealSelectionDto);
        }

        return dtoList;
    }

    @Transactional
    @Override
    public IdealSelectionDto create(String title, String body, List<MultipartFile> files, HttpServletRequest request){
        User user = sessionManager.getSession(request).orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER));
        IdealSelection selection = IdealSelection.builder()
                .title(title).body(body).creator(user).build();

        selectionMapper.save(selection);
        Path path = Paths.get(rootFilePath, selection.getFilePath());
        Path absolutePath = path.toAbsolutePath();

        List<Ideal> idealList = new ArrayList<>();
        try {
            Files.createDirectories(absolutePath);
            for (MultipartFile file : files) {
                String name = file.getOriginalFilename();
                Path imagePath = Paths.get(absolutePath.toString(), name);
                file.transferTo(new File(imagePath.toString()));
                Ideal ideal = Ideal.builder().idealName(name).winCount(0).finalWinCount(0).battleCount(0).selectionId(selection.getId()).build();
                idealList.add(ideal);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        idealMapper.saveAll(idealList);
        return new IdealSelectionDto(selection);
    }

    @Override
    public IdealSelectionDto update(IdealSelectionDto idealSelection, HttpServletRequest request) {
        return null;
    }

    @Override
    public void delete(Long id, HttpServletRequest request) {
        User user = sessionManager.getSession(request).orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER));
        IdealSelection selection = selectionMapper.findByIdAllResult(id).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        if(!selection.getCreator().getId().equals(user.getId()))
            throw new CustomException(ErrorCode.BAD_REQUEST);

        selectionMapper.deleteById(id);
    }


    @Override
    public void updateWinCount(Long winIdealId, IdealSelectionDto selectionDto, HttpServletRequest request) {
        IdealSelection selection = selectionMapper.findByIdAllResult(selectionDto.getId()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        List<Ideal> idealList = new ArrayList<>();

        selection.setSubCount(selection.getSubCount() + 1);
        for (Ideal ideal : selectionDto.getIdealList()) {
            Ideal updateIdeal = idealMapper.findById(ideal.getId()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
            updateIdeal.setWinCount(updateIdeal.getWinCount() + ideal.getWinCount());
            updateIdeal.setBattleCount(updateIdeal.getBattleCount() + ideal.getBattleCount());
            if(ideal.getId().equals(winIdealId)) {
                updateIdeal.setFinalWinCount(updateIdeal.getFinalWinCount() + 1);
            }
            idealList.add(updateIdeal);
        }
        idealMapper.saveAll(idealList);
        selectionMapper.update(selection.getId(),selection);
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

    public byte[] getIdealImg(String selectionPath, String idealName) throws IOException {

        Path imagePath = Paths.get(rootFilePath, selectionPath, idealName).toAbsolutePath();
        Resource imageResource = new UrlResource(imagePath.toUri());
        if (imageResource.exists()) {
            return Files.readAllBytes(imagePath);
        } else {
            throw new CustomException(ErrorCode.FILE_NOT_FOUND);
        }

    }

    @Override
    public IdealSelectionDto getSelection(Long selectionId, HttpServletRequest request){
        User user = sessionManager.getSession(request).get();

        IdealSelection selection = selectionMapper.findByIdAllResult(selectionId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        return new IdealSelectionDto(selection);
    }
    @Override
    public IdealSelectionDto getSelection(Long selectionId){
        IdealSelection selection = selectionMapper.findByIdAllResult(selectionId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        return new IdealSelectionDto(selection);
    }



    @Override
    public IdealSelectionDto getPlayableSelection(Long selectionId, int round, HttpServletRequest request){
        User user = sessionManager.getSession(request).orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER));
        IdealSelection selection = selectionMapper.findByIdAllResult(selectionId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        List<Ideal> idealList = new ArrayList<>(selection.getIdealList());

        Collections.shuffle(idealList);
        List<Ideal> randomIdealList = idealList.subList(0, round);
        selection.setIdealList(randomIdealList);


        return new IdealSelectionDto(selection);
    }
}
