package com.alibou.security.controller;

import com.alibou.security.entity.AnswerEntity;
import com.alibou.security.service.AnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answer")
@CrossOrigin
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;



    @GetMapping("/by-exercise/{id}")
    private ResponseEntity<List<AnswerEntity>> getAnswers(@PathVariable @Valid Long id){



        return ResponseEntity.ok(answerService.getAnswersById(id));

    }


}
