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
    private String finalWinCount;
    private Integer winCount;
    private Integer battleCount;
    private Long selectionId;

    public IdealDto(Ideal ideal){
        this.id = ideal.getId();
        this.idealName = ideal.getIdealName();
        this.winCount = ideal.getWinCount();
        this.finalWinCount = ideal.getFinalWinCount();
        this.battleCount = ideal.getBattleCount();
        this.selectionId = ideal.getSelectionId();
    }
}
