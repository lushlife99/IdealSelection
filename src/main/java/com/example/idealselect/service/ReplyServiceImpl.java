package com.example.idealselect.service;

import com.example.idealselect.dto.ReplyDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    @Override
    public List<ReplyDto> create(ReplyDto replyDto, Long idealSelectionId, HttpServletRequest request) {
        return null;
    }

    @Override
    public List<ReplyDto> update(ReplyDto replyDto, Long idealSelectionId, HttpServletRequest request) {
        return null;
    }

    @Override
    public List<ReplyDto> delete(ReplyDto replyDto, Long idealSelectionId, HttpServletRequest request) {
        return null;
    }
}
