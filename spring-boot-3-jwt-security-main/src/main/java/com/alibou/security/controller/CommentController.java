package com.alibou.security.controller;

import com.alibou.security.entity.ExerciseCommentEntity;
import com.alibou.security.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    private final CommentService service;


    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping("/post")
    public ResponseEntity<?> postReview( @RequestBody @Valid ExerciseCommentEntity entity){

        service.postReview(entity);

        return ResponseEntity.accepted().build();
    }



}
