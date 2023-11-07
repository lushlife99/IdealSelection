package com.example.idealselect.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String filePath;
    private List<Ideal> idealList;
    private int subCount;
    private User creator;
    private List<Reply> replyList;
}
