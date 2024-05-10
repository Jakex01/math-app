package com.alibou.security.dao;

import com.alibou.security.entity.AnswerEntity;
import com.alibou.security.entity.ExerciseCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
    @Query("SELECT ec FROM AnswerEntity ec WHERE ec.exercise1.exercise_id = :exerciseId")
    List<AnswerEntity> findAnswerEntitiesByExerciseId(@Param("exerciseId") Long exerciseId);



}
