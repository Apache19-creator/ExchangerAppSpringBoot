package com.herotech.app.config;


import com.herotech.data.entities.APIError;
import com.herotech.data.exceptions.HeroXchangeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<APIError> handleExceptions(HeroXchangeException e) {
        log.error(e.getLocalizedMessage());
        return ResponseEntity.badRequest().body(APIError.builder()
                .message(e.getLocalizedMessage())
                .ok(false)
                .status(HttpStatus.BAD_REQUEST)
                .build());
    }


}
