package com.example.idealselect.entity;

import lombok.Data;

@Data
public class Ideal {

    private Long id;
    private String idealName;
    private Double winRate;
    private IdealSelection selection;
}
