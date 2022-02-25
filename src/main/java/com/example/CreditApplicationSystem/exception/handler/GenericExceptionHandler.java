package com.example.CreditApplicationSystem.exception.handler;

import com.example.CreditApplicationSystem.exception.AlreadyExistException;
import com.example.CreditApplicationSystem.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map> handleNotFoundException(NotFoundException exception){
        Map<String,String> response=new HashMap<>();
        response.put("message",exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Map> handleAlreadyExistException(AlreadyExistException exception){
        Map<String,String> response=new HashMap<>();
        response.put("message",exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map> handleException(Exception exception){
        Map<String,String> response=new HashMap<>();
        response.put("message",exception.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}
