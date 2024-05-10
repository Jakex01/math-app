package com.alibou.security.service;

import com.alibou.security.dao.ExerciseRepository;
import com.alibou.security.dao.ExerciseCommentRepository;
import com.alibou.security.dao.UserRepository;
import com.alibou.security.entity.ExerciseCommentEntity;
import com.alibou.security.entity.ExerciseEntity;
import com.alibou.security.entity.UserEntity;
import com.alibou.security.exceptions.ExerciseNotFoundException;
import com.alibou.security.exceptions.UserNotFoundException;
import com.alibou.security.mapstruct.dto.CommentDto;
import com.alibou.security.mapstruct.mapper.MapStructMapper;
import com.alibou.security.request.ChangePasswordRequest;
import com.alibou.security.request.PostCommentRequest;
import com.alibou.security.validators.ObjectsValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final ExerciseCommentRepository exerciseCommentRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
    private final ObjectsValidator<PostCommentRequest> validator;


    public ResponseEntity<?> postReview(PostCommentRequest commentRequest,
                           Authentication connectedUser
    ) {
        validator.validate(commentRequest);

        UserEntity principal = (UserEntity) connectedUser.getPrincipal();

        UserEntity user = userRepository.findById(principal.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));


        ExerciseEntity exerciseEntity = exerciseRepository.findById(commentRequest.getExerciseId())
                .orElseThrow(() -> new ExerciseNotFoundException("Exercise not found"));


        ExerciseCommentEntity exerciseComment =  ExerciseCommentEntity.builder()
                .body(commentRequest.getBody())
                .rating(commentRequest.getRating())
                .user(user)
                .exercise(exerciseEntity)
                .build();



        exerciseCommentRepository.save(exerciseComment);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    public List<CommentDto> getExerciseComments(Long exerciseId) {

        return exerciseCommentRepository
                .findExerciseCommentEntitiesByExerciseId(exerciseId)
                .stream()
                .map(MapStructMapper.INSTANCE::commentToCommentDto)
                .collect(Collectors.toList());

    }

}
