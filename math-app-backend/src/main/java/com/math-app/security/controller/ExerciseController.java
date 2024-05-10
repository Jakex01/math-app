package com.alibou.security.controller;


import com.alibou.security.entity.ExerciseCommentEntity;
import com.alibou.security.entity.ExerciseEntity;
import com.alibou.security.mapstruct.dto.ExerciseDto;
import com.alibou.security.service.ExerciseService;
import jakarta.validation.constraints.NotNull;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/exercise")
@CrossOrigin
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ExerciseDto>> getAllExercises(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10")int size
    ){

        return ResponseEntity.ok(exerciseService.findAllExercises(PageRequest.of(page, size)));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<ExerciseEntity>> findFilteredExercises(
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10")int size) {


        return ResponseEntity.ok(exerciseService.findExercisesFiltered(level, category, PageRequest.of(page,size)));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ExerciseDto>> findByIdExercise(
            @PathVariable @NotNull Long id
    ){
        return ResponseEntity.ok(exerciseService.findByIdExercise(id));
    }


    @GetMapping("/{id}/comments")
    public ResponseEntity<Set<ExerciseCommentEntity>> findComments(@PathVariable @NotNull Long id){

        return new ResponseEntity<>(exerciseService.exerciseCommentEntities(id), HttpStatus.OK);

    }


}
