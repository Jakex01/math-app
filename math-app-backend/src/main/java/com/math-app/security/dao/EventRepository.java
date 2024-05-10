package com.alibou.security.dao;

import com.alibou.security.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long>, JpaSpecificationExecutor<EventEntity> {

    List<EventEntity> findByLevelAndDate(String level, LocalDate date);
    List<EventEntity> findByLevel(String level);
    List<EventEntity> findByDate(LocalDate date);

}
