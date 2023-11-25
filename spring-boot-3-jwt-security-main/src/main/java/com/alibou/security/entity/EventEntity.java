package com.alibou.security.entity;


import com.alibou.security.entity.enums.Level;
import com.alibou.security.entity.enums.StudyingLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="event")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String category;
    @NotBlank
    private String description;

    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private StudyingLevel studying_level;
    @Enumerated(EnumType.STRING)
    @NotBlank
    private Level level;
    @NotNull
    private  double duration;
    @NotNull
    private int price_full_access;
    @NotNull
    private int price_one_time_access;
    @NotBlank
    private String url;
    @NotBlank
    private String lecturer;

    @CreatedDate
    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModified;

    @CreatedDate
    @Column(
            nullable = false,
            updatable = false
    )
    private Long createdBy;
    @LastModifiedDate
    @Column(insertable = false)
    private Long lastModifiedBy;

}
