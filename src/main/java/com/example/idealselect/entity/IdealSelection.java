package com.example.idealselect.entity;

import lombok.Data;

import java.util.List;

/**
 * subCount -> SubMission Count.
 * 1. 인기순 정렬할 때 사용
 * 2. WinRate 계산할 때 사용
 */
@Data
public class IdealSelection {

    private Long id;

    private String title;
    private String body;
    private List<Integer> winCount;

    private List<Ideal> idealList;
    private int subCount;
    private User creator;
    private List<Reply> replyList;
}
