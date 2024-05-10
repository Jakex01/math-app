package com.alibou.security.dao;

import com.alibou.security.entity.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long>, JpaSpecificationExecutor<ExerciseEntity> {
}
