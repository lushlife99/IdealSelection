package com.example.idealselect.dto;

import com.example.idealselect.entity.Ideal;
import com.example.idealselect.entity.IdealSelection;
import com.example.idealselect.entity.Reply;
import com.example.idealselect.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class IdealSelectionDto {

    private Long id;
    private String title;
    private String body;
    private String filePath;
    private int subCount;
    private User creator;
    private List<Ideal> idealList;
    private List<Reply> replyList;
    private LocalDateTime updateTime;
    private String img1;
    private String img2;


    public IdealSelectionDto(IdealSelection selection){
        this.id = selection.getId();
        this.title = selection.getTitle();
        this.body = selection.getBody();
        this.filePath = selection.getFilePath();
        this.subCount = selection.getSubCount();
        this.idealList = selection.getIdealList();
        this.replyList = selection.getReplyList();
        this.creator = selection.getCreator();
        this.updateTime = selection.getUpdateTime();
        if(idealList.size() > 1) {
            this.img1 = selection.getFilePath() + "/" + selection.getIdealList().get(0).getIdealName();
            this.img2 = selection.getFilePath() + "/" + selection.getIdealList().get(1).getIdealName();
        }

    }
}
