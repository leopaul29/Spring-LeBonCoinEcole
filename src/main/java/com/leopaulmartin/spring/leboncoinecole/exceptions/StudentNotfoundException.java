package com.leopaulmartin.spring.leboncoinecole.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class StudentNotfoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
}
