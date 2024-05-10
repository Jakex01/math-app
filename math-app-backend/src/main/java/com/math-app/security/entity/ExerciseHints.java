package com.alibou.security.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "exercise_hints")
public class ExerciseHints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hint_id;
    private String hint;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private ExerciseEntity exercise;

}
