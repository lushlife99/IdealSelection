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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void create(ReplyDto replyDto, HttpServletRequest request) {
        User user = sessionManager.getSession(request).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        Reply reply = new Reply(replyDto);
        reply.setUserId(user.getId());
        reply.setUserName(user.getUserName());
        reply.setUpdateTime(LocalDateTime.now());

        replyMapper.save(reply);
    }

    @Override
    @Transactional
    public void update(ReplyDto replyDto, HttpServletRequest request) {

        User user = sessionManager.getSession(request).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        Reply reply = replyMapper.findById(replyDto.getId()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        if(!reply.getUserId().equals(user.getId()))
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);

        reply.setComment(replyDto.getComment());
        reply.setUpdateTime(LocalDateTime.now());
        replyMapper.save(reply);

    }

    @Override
    @Transactional
    public void delete(ReplyDto replyDto, HttpServletRequest request) {
        User user = sessionManager.getSession(request).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        Reply reply = replyMapper.findById(replyDto.getId()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        if(!reply.getUserId().equals(user.getId()))
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);

        replyMapper.deleteById(reply.getId());
    }

    @Override
    public List<ReplyDto> getReplyList(Long idealSelectionId, int pageNum, HttpServletRequest request){

        List<Reply> replyList = replyMapper.findLimitListBySelectionId(idealSelectionId, pageNum);
        List<ReplyDto> replyDtoList = new ArrayList<>();

        for (Reply reply : replyList) {
            replyDtoList.add(new ReplyDto(reply));
        }
        return replyDtoList;
    }
}
