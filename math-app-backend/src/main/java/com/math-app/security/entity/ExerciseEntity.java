package com.alibou.security.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="exercise")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exercise_id;

    @NotBlank
    private String category;
    @NotBlank
    private String level;
    @NotBlank
    private String description;
    @NotNull
    private int time_allocated;

    private String img_url;

    private Double averageRating;

//    @ElementCollection
//    @CollectionTable(name = "exercise_hints", joinColumns = @JoinColumn(name = "exercise_id"))
//    @Column(name = "hint")
//    private List<String> hints;


    @JsonBackReference
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ExerciseHints> exerciseHints;



    @JsonBackReference
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ExerciseCommentEntity> exerciseCommentEntities;





}
