package com.example.idealselect.dto;

import com.example.idealselect.entity.IdealSelection;
import com.example.idealselect.entity.Reply;
import com.example.idealselect.entity.User;

import java.util.List;

public class UserDto {

    private Long id;
    private String userId;
    private String userName;
    private List<IdealSelection> madeIdealList;
    private List<Reply> replyList;

    public UserDto(User user){
        this.id = user.getId();
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.madeIdealList = user.getMadeIdealList();
        this.replyList = user.getReplyList();
    }
}
