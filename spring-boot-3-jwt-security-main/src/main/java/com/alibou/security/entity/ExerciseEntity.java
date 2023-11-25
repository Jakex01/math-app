package com.alibou.security.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
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
    private Instant start_time;
    private Instant end_time;
    private Double averageRating;
    @ElementCollection
    @CollectionTable(name = "exercise_hints", joinColumns = @JoinColumn(name = "exercise_id"))
    @Column(name = "hint")
    private List<String> hints;

    @ElementCollection
    @CollectionTable(name = "answer", joinColumns = @JoinColumn(name = "exercise_id"))
    @Column(name = "text")
    private Set<String> answers;

    private Long duration;

    @JsonBackReference
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ExerciseCommentEntity> exerciseCommentEntities;



    @PrePersist
    @PreUpdate
    private void calculateDuration() {
        if (start_time != null && end_time != null) {
            this.duration = Duration.between(start_time, end_time).getSeconds();
        } else {
            this.duration = null;
        }
    }


}
