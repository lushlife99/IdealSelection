package com.example.idealselect.service;

import com.example.idealselect.dto.IdealSelectionDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SelectionServiceImpl implements IdealSelectionService{

    @Override
    public List<IdealSelectionDto> getByPopularity(HttpServletRequest request) {
        return null;
    }

    @Override
    public List<IdealSelectionDto> getByLatest(HttpServletRequest request) {
        return null;
    }

    @Override
    public IdealSelectionDto create(IdealSelectionDto idealSelectionDto, HttpServletRequest request) {
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
    public List<IdealSelectionDto> getCreationList(HttpServletRequest request) {
        return null;
    }
}
