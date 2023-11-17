package com.example.idealselect.controller.api;

import com.example.idealselect.dto.ReplyDto;
import com.example.idealselect.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/reply")
@RequiredArgsConstructor
@RestController
public class ReplyApiController {

    private final ReplyService replyService;

    @PostMapping("/save")
    public List<ReplyDto> saveReply(@RequestBody ReplyDto replyDto, @RequestParam(defaultValue = "0") int pageNum, HttpServletRequest request){
        replyService.create(replyDto, request);
        return replyService.getReplyList(replyDto.getIdealSelectionId(), pageNum, request);
    }

    @PutMapping("/update")
    public List<ReplyDto> updateReply(@RequestBody ReplyDto replyDto, @RequestParam(defaultValue = "0") int pageNum, HttpServletRequest request){
        replyService.update(replyDto, request);
        return replyService.getReplyList(replyDto.getIdealSelectionId(), pageNum, request);
    }

    @DeleteMapping("/delete")
    public List<ReplyDto> deleteReply(@RequestBody ReplyDto replyDto, @RequestParam(defaultValue = "0") int pageNum, HttpServletRequest request){
        replyService.delete(replyDto, request);
        return replyService.getReplyList(replyDto.getIdealSelectionId(), pageNum, request);
    }
}
