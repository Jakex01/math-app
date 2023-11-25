package com.alibou.security.service;

import com.alibou.security.entity.ExerciseCommentEntity;
import com.alibou.security.dao.ExerciseCommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class CommentService {

    private final ExerciseCommentRepository exerciseCommentRepository;

    public CommentService(ExerciseCommentRepository exerciseCommentRepository) {
        this.exerciseCommentRepository = exerciseCommentRepository;
    }


    public void postReview(ExerciseCommentEntity entity) {

        exerciseCommentRepository.save(entity);

    }
}
