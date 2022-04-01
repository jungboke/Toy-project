package com.example.baekshopV2.exception.advice;

import com.example.baekshopV2.exception.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> illegalArgExHandler(IllegalArgumentException e) {
        log.error("[ exceptionHandler ] ex", e);
        ErrorResult errorResult = new ErrorResult("400", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> illegalStExHandler(IllegalStateException e) {
        log.error("[ exceptionHandler ] ex", e);
        ErrorResult errorResult = new ErrorResult("401", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> Exhandler(Exception e) {
        log.error("[ exceptionHandler ] ex", e);
        ErrorResult errorResult = new ErrorResult("600", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }
}
