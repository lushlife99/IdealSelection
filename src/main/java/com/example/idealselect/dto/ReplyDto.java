package com.example.idealselect.dto;

import com.example.idealselect.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ReplyDto {

    private Long id;
    private Long userId;
    private String comment;
    private String userName;
    private Long idealSelectionId;
    private LocalDateTime updateTime;

    public ReplyDto(Reply reply){
        this.id = reply.getId();
        this.userId = reply.getUserId();
        this.comment = reply.getComment();
        this.userName = reply.getUserName();
        this.idealSelectionId = reply.getIdealSelectionId();
        this.updateTime = reply.getUpdateTime();
    }
}
