package com.example.idealselect.service;

import com.example.idealselect.dto.ReplyDto;
import com.example.idealselect.entity.Reply;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ReplyService {

    List<ReplyDto> create(ReplyDto replyDto, Long idealSelectionId, HttpServletRequest request);
    List<ReplyDto> update(ReplyDto replyDto, Long idealSelectionId, HttpServletRequest request);
    List<ReplyDto> delete(ReplyDto replyDto, Long idealSelectionId, HttpServletRequest request);
}
