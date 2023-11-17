package com.example.idealselect.service;

import com.example.idealselect.dto.ReplyDto;
import com.example.idealselect.entity.Reply;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ReplyService {

    void create(ReplyDto replyDto, Long idealSelectionId, HttpServletRequest request);
    void update(ReplyDto replyDto, Long idealSelectionId, HttpServletRequest request);
    void delete(ReplyDto replyDto, Long idealSelectionId, HttpServletRequest request);
    List<ReplyDto> getReplyList(Long idealSelectionId, HttpServletRequest request);
}
