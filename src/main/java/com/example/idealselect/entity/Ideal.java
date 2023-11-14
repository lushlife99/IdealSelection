package com.example.idealselect.entity;

import com.example.idealselect.dto.IdealDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Ideal {

    private Long id;
    private String idealName;
    private String finalWinCount;
    private Integer winCount;
    private Integer battleCount;
    private Long selectionId;

    public Ideal(IdealDto idealDto){
        this.idealName = idealDto.getIdealName();
        this.selectionId = idealDto.getSelectionId();
        this.finalWinCount = idealDto.getFinalWinCount();
        this.winCount = idealDto.getWinCount();
        this.battleCount = idealDto.getBattleCount();

    }

}
