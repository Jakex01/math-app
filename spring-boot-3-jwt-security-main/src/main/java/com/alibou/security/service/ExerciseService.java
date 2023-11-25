package com.alibou.security.service;

import com.alibou.security.entity.ExerciseCommentEntity;
import com.alibou.security.entity.ExerciseEntity;
import com.alibou.security.exceptions.ExerciseNotFoundException;
import com.alibou.security.mapstruct.dto.ExerciseDto;
import com.alibou.security.mapstruct.mapper.MapStructMapper;
import com.alibou.security.dao.ExerciseRepository;
import com.alibou.security.validators.ObjectsValidator;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ObjectsValidator<ExerciseEntity>  exerciseEntityObjectsValidator;

    public Page<ExerciseDto> findAllExercises(Pageable pageable){

        return exerciseRepository
                .findAll(pageable)
                .map(MapStructMapper.INSTANCE::exerciseToExerciseDto);

    }

    public Page<ExerciseEntity> findExercisesFiltered(String level,
                                                      String category,
                                                      Pageable pageable){

        return exerciseRepository.findAll(createExerciseSpecification(level,category), pageable);
    }


    public Optional<ExerciseEntity> findByIdExercise(Long id) {


        return Optional
                .ofNullable(exerciseRepository
                        .findById(id)
                        .map(exerciseEntity -> {
                            exerciseEntity.setStart_time(Instant.now());
                            exerciseRepository.save(exerciseEntity);

                            exerciseEntityObjectsValidator.validate(exerciseEntity);

                            return exerciseEntity;
                })
                        .orElseThrow(() -> new ExerciseNotFoundException("Exercise not found with id: " + id)));

    }

    public Set<ExerciseCommentEntity> exerciseCommentEntities(Long id){

        return exerciseRepository.findById(id)
                .get()
                .getExerciseCommentEntities();


    }

    public void setExerciseEndTime(Long id){

        exerciseRepository
                .findById(id)
                .ifPresent(exercise -> {
                            exercise.setEnd_time(Instant.now());
                            exercise.setDuration(Duration
                                    .between(exercise.getStart_time()
                                            ,exercise.getEnd_time())
                                    .getSeconds());

                            exerciseRepository.save(exercise);
                        }
                );


    }

    private Specification<ExerciseEntity> createExerciseSpecification(String level, String category) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Optional.ofNullable(level)
                    .ifPresent(l -> predicates.add(cb.equal(root.get("level"), l)));

            Optional.ofNullable(category)
                    .ifPresent(k -> predicates.add(cb.equal(root.get("category"), k)));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
