package com.alibou.security.dao;

import com.alibou.security.entity.ExerciseCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ExerciseCommentRepository extends JpaRepository<ExerciseCommentEntity, Integer> {

}
