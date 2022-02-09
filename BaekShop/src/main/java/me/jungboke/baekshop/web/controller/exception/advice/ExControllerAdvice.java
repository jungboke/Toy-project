package me.jungboke.baekshop.web.controller.exception.advice;

import lombok.extern.slf4j.Slf4j;
import me.jungboke.baekshop.web.controller.exception.api.ApiExController;
import me.jungboke.baekshop.web.controller.exception.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "me.jungboke.baekshop.web.controller.exception.api")
public class ExControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER_EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> RunTimeExHandler(RuntimeException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("RUNTIME_EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_GATEWAY);
    }
}
