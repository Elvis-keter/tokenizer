package com.elewa.assignment.advice;

import com.elewa.assignment.exception.RefreshTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class MainExceptionHandler {
    @ExceptionHandler(value = RefreshTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleTokenRefreshException(RefreshTokenException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }

}

//eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraXJhIiwiZXhwIjoxNzI0MDcxNzgzLCJpYXQiOjE3MjQwNzE0ODN9.RKdPUVxVmlLRwkyMsFigMzYd6HjKOI2L2CrmvNNCmBA