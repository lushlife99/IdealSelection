package com.example.idealselect.service;

import com.example.idealselect.dto.IdealSelectionDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IdealSelectionService {

    List<IdealSelectionDto> getByPopularity(HttpServletRequest request);
    List<IdealSelectionDto> getByLatest(HttpServletRequest request);
    IdealSelectionDto create(String title, String body, List<MultipartFile> files, HttpServletRequest request);
    IdealSelectionDto update(IdealSelectionDto idealSelection, HttpServletRequest request);
    void delete(Long id, HttpServletRequest request);

    IdealSelectionDto getRanking(HttpServletRequest request);
    void updateWinRate(Long idealSelectionId, Long winIdealId, Long defeatIdealId, HttpServletRequest request);
    void updateWinCount(Long idealSelectionId, Long idealId, HttpServletRequest request);
    void updateSubCount(Long idealSelectionId, HttpServletRequest request);
    List<IdealSelectionDto> getCreationList(HttpServletRequest request);
}
