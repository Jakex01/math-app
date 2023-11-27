package com.alibou.security.dao;

import com.alibou.security.entity.ExerciseCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExerciseCommentRepository extends JpaRepository<ExerciseCommentEntity, Integer> {

    @Query("SELECT ec FROM ExerciseCommentEntity ec WHERE ec.exercise.id = :exerciseId")
    List<ExerciseCommentEntity> findExerciseCommentEntitiesByExerciseId(@Param("exerciseId") Long exerciseId);



}
