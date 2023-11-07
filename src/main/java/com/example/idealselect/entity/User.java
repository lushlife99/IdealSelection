package com.example.idealselect.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;
    private String userId;
    private String password;
    private String userName;
    private List<IdealSelection> madeIdealList;
    private List<Reply> replyList;

}
