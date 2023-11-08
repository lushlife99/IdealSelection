package com.example.idealselect.entity;

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
}
