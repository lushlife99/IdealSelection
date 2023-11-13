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
    private Double winRate;
    private Integer winCount;
    private IdealSelection selection;

    public Ideal(IdealDto idealDto){
        this.idealName = idealDto.getIdealName();
        this.winRate = idealDto.getWinRate();
        this.winCount = idealDto.getWinCount();
    }
}
