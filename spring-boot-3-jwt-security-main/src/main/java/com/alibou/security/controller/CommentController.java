package com.alibou.security.controller;

import com.alibou.security.entity.ExerciseCommentEntity;
import com.alibou.security.mapstruct.dto.CommentDto;
import com.alibou.security.request.PostCommentRequest;
import com.alibou.security.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    private final CommentService service;


    public CommentController(CommentService service) {
        this.service = service;
    }


    @GetMapping("/{ExerciseId}")
    public ResponseEntity<List<CommentDto>> getExerciseComments(@PathVariable @Valid Long ExerciseId){
        return ResponseEntity.ok(service.getExerciseComments(ExerciseId));

    }
    @PostMapping("/post")
    public ResponseEntity<?> postComment(@RequestBody @Valid PostCommentRequest entity,
                                         Authentication authentication
                                         ) {



        return service.postReview(entity, authentication);

    }


}
