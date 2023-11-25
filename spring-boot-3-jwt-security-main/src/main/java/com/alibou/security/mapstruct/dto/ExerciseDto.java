package com.alibou.security.mapstruct.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseDto {

    private Long id;
    private String category;
    private String level;
    private String description;
    private int time_allocated;
    private Double averageRating;

}
