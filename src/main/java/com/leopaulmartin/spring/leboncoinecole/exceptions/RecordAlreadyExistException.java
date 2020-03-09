package com.leopaulmartin.spring.leboncoinecole.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class RecordAlreadyExistException extends RuntimeException {

	public RecordAlreadyExistException(String message) {
		super(message);
	}
}
