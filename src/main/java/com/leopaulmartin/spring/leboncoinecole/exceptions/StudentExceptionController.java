package com.leopaulmartin.spring.leboncoinecole.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//The @ExceptionHandler is an annotation used to handle the specific exceptions and sending the custom responses to the client.
@ControllerAdvice
public class StudentExceptionController {

    @ExceptionHandler(value = StudentNotfoundException.class)
    public ResponseEntity<Object> exception(StudentNotfoundException exception) {
        return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
    }
}
