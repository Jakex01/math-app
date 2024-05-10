package com.alibou.security.handler;

import com.alibou.security.exceptions.ExerciseNotFoundException;
import com.alibou.security.exceptions.ObjectNotValidException;
import com.alibou.security.exceptions.UserExerciseException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleException(IllegalStateException exception){

        return
                ResponseEntity
                        .badRequest()
                        .body(exception.getMessage());

    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleException(){

        return
                ResponseEntity
                        .notFound()
                        .build();

    }
    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<?> handleException(ObjectNotValidException exception){

        return
                ResponseEntity
                        .badRequest()
                        .body(exception.getErrorMessages());

    }
    @ExceptionHandler(UserExerciseException.class)
    public ResponseEntity<?> handleUserExerciseException(UserExerciseException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
    @ExceptionHandler(ExerciseNotFoundException.class)
    public ResponseEntity<?> handleExerciseNotFoundException(ExerciseNotFoundException ex) {
        // You can customize the response as needed
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
