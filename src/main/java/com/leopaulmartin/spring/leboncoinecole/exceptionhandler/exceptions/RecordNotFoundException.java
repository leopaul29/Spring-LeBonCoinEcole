package com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {

	private static final String FIELD_NOTFOUND_EXCEPTION_MESSAGE = "Invalid %s => %s";

	public RecordNotFoundException(String msg) {
		super(msg);
	}

	public RecordNotFoundException(String fieldName, Object fieldTValue) {
		super(String.format(FIELD_NOTFOUND_EXCEPTION_MESSAGE, fieldName, fieldTValue.toString()));
	}
}