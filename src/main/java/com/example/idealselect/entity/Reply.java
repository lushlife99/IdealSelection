package com.example.idealselect.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reply {

    private Long id;
    private String comment;
    private User author;
    private Long idealSelectionId;
    private LocalDateTime updateTime;
}

