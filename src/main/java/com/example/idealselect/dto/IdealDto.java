package com.example.idealselect.dto;

import com.example.idealselect.entity.Ideal;
import com.example.idealselect.entity.IdealSelection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class IdealDto {
    private Long id;
    private String idealName;
    private Double winRate;
    private Integer winCount;
    private Long selectionId;

    public IdealDto(Ideal ideal){
        this.id = ideal.getId();
        this.idealName = ideal.getIdealName();
        this.winCount = ideal.getWinCount();
        this.winRate = ideal.getWinRate();
        this.selectionId = ideal.getSelection().getId();
    }
}
