package com.alibou.security.service;

import com.alibou.security.dao.AnswerRepository;
import com.alibou.security.entity.AnswerEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional

public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<AnswerEntity> getAnswersById(Long id) {


        return answerRepository
                .findAnswerEntitiesByExerciseId(id);
    }
}
