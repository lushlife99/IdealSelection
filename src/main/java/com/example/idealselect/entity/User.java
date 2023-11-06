package com.example.idealselect.entity;

import lombok.Data;

import java.util.List;


@Data
public class User {

    private Long id;
    private String userId;
    private String password;
    private String userName;
    private List<IdealSelection> madeIdealList;
    private List<Reply> replyList;

}
