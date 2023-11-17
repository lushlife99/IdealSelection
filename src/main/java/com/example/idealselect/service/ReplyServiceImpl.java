package com.example.idealselect.service;

import com.example.idealselect.dto.ReplyDto;
import com.example.idealselect.entity.IdealSelection;
import com.example.idealselect.entity.Reply;
import com.example.idealselect.entity.User;
import com.example.idealselect.exception.CustomException;
import com.example.idealselect.exception.ErrorCode;
import com.example.idealselect.repository.IdealSelectionMapper;
import com.example.idealselect.repository.ReplyMapper;
import com.example.idealselect.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final IdealSelectionMapper selectionMapper;
    private final ReplyMapper replyMapper;
    private final SessionManager sessionManager;

    @Override
    public void create(ReplyDto replyDto, Long idealSelectionId, HttpServletRequest request) {
        User user = sessionManager.getSession(request).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        Reply reply = new Reply(replyDto);
        reply.setUserId(user.getId());
        reply.setUserName(user.getUserName());
        reply.setUpdateTime(LocalDateTime.now());

        replyMapper.save(reply);
    }

    @Override
    public void update(ReplyDto replyDto, Long idealSelectionId, HttpServletRequest request) {

        User user = sessionManager.getSession(request).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        Reply reply = replyMapper.findById(replyDto.getId()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        if(!reply.getUserId().equals(user.getId()))
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);

        reply.setComment(replyDto.getComment());
        reply.setUpdateTime(LocalDateTime.now());
        replyMapper.save(reply);

    }

    @Override
    public void delete(ReplyDto replyDto, Long idealSelectionId, HttpServletRequest request) {
        User user = sessionManager.getSession(request).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        Reply reply = replyMapper.findById(replyDto.getId()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        if(!reply.getUserId().equals(user.getId()))
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);

        replyMapper.deleteById(reply.getId());
    }

    @Override
    public List<ReplyDto> getReplyList(Long idealSelectionId, HttpServletRequest request){
        IdealSelection selection = selectionMapper.findByIdAllResult(idealSelectionId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        List<ReplyDto> replyList = new ArrayList<>();

        for (Reply reply : selection.getReplyList()) {
            replyList.add(new ReplyDto(reply));
        }
        return replyList;
    }
}
