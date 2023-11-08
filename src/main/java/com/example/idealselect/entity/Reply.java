package com.example.idealselect.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reply {

    private Long id;
    private Long userId;
    private String comment;
    private String userName;
    private Long idealSelectionId;
    private LocalDateTime updateTime;
}

