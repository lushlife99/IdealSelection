package com.example.idealselect.entity;

import com.example.idealselect.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    @Builder.Default
    private List<IdealSelection> madeIdealList = new ArrayList<>();
    @Builder.Default
    private List<Reply> replyList = new ArrayList<>();


}
