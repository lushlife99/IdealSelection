package com.example.idealselect.entity;

import com.example.idealselect.dto.ReplyDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reply {

    private Long id;
    private Long userId;
    private String comment;
    private String userName;
    private Long idealSelectionId;
    private LocalDateTime updateTime;

    public Reply(ReplyDto replyDto) {
        this.comment = replyDto.getComment();
        this.idealSelectionId = replyDto.getIdealSelectionId();
    }

}

