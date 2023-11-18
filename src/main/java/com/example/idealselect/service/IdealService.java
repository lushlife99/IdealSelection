package com.example.idealselect.service;

import com.example.idealselect.dto.IdealDto;
import com.example.idealselect.entity.Ideal;
import com.example.idealselect.entity.IdealSelection;
import com.example.idealselect.entity.User;
import com.example.idealselect.exception.CustomException;
import com.example.idealselect.exception.ErrorCode;
import com.example.idealselect.repository.IdealMapper;
import com.example.idealselect.repository.IdealSelectionMapper;
import com.example.idealselect.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdealService {

    private final IdealMapper idealMapper;
    private final IdealSelectionMapper selectionMapper;
    private final SessionManager sessionManager;
    @Value("${file:}")
    private String rootFilePath;


    public IdealDto updateImage(String filePath, Long idealId, Long selectionId, MultipartFile updateFile, HttpServletRequest request) {
        User user = sessionManager.getSession(request).orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER));
        IdealSelection selection = selectionMapper.findByIdAllResult(selectionId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        Ideal ideal = idealMapper.findById(idealId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        if (!selection.getCreator().getId().equals(user.getId()))
            throw new CustomException(ErrorCode.BAD_REQUEST);

        try {
            Path path = Paths.get(rootFilePath, filePath, ideal.getIdealName());
            updateFile.transferTo(path.toFile());

            return new IdealDto(ideal);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public void delete(Long id, HttpServletRequest request){
        User user = sessionManager.getSession(request).orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED_USER));
        Ideal ideal = idealMapper.findById(id).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        IdealSelection selection = selectionMapper.findByIdAllResult(ideal.getSelectionId()).orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));
        if(!selection.getCreator().getId().equals(user.getId()))
            throw new CustomException(ErrorCode.BAD_REQUEST);

        idealMapper.deleteById(id);
    }

    public void add(MultipartFile file, Long selectionId, HttpServletRequest request){
        IdealSelection selection = selectionMapper.findByIdAllResult(selectionId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        User user = sessionManager.getSession(request).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        if(!selection.getCreator().getId().equals(user.getId()))
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);

        Ideal ideal = Ideal.builder().idealName(file.getOriginalFilename()).selectionId(selectionId).winCount(0).finalWinCount(0).battleCount(0).build();

        try {
            Path path = Paths.get(rootFilePath, selection.getFilePath(), file.getOriginalFilename());
            file.transferTo(path.toFile());
            idealMapper.save(ideal);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
