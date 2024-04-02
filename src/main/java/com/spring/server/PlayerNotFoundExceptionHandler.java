package com.spring.server;

import java.time.ZonedDateTime;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PlayerNotFoundExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> playerNotFoundHandler (PlayerNotFoundException ex, HttpServletRequest req) {
        ErrorResponse error = new ErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                req.getRequestURI(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
