package com.elewa.assignment.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
public class JwtExceptionHandler {
    @ExceptionHandler(value = {SignatureException.class})
    public ResponseEntity<Object> signatureException(SignatureException e) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Invalid signature token");
    }

    @ExceptionHandler(value = {MalformedJwtException.class})
    public ResponseEntity<Object> malformedException(MalformedJwtException e) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Invalid Jwt token");
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<Object> expiredException(ExpiredJwtException e) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Jwt token has expired");
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<Object> usernameException(UsernameNotFoundException e) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Token is not in DB");
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> argumentException(IllegalArgumentException e) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Jwt claims string is empty");
    }
    @ExceptionHandler(value = {UnsupportedJwtException.class})
    public ResponseEntity<Object> unsupportedException(UnsupportedJwtException e) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Token is unsupported");
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);

        return new ResponseEntity<>(response, status);
    }

}