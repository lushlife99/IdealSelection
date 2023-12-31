package com.example.idealselect.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * subCount -> SubMission Count.
 * 1. 인기순 정렬할 때 사용
 * 2. WinRate 계산할 때 사용
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class IdealSelection {

    private Long id;
    private String title;
    private String body;
    @Builder.Default
    private String filePath = UUID.randomUUID().toString();
    @Builder.Default
    private List<Ideal> idealList = new ArrayList<>();
    @Builder.Default
    private List<Reply> replyList = new ArrayList<>();
    private int subCount;
    private User creator;
    @Builder.Default
    private LocalDateTime updateTime = LocalDateTime.now();
}
