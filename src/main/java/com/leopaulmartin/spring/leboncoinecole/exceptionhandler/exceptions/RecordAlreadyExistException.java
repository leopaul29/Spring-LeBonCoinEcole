package com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class RecordAlreadyExistException extends RuntimeException {

	//	https://www.javatpoint.com/java-string-format
	private static final String FIELD_ALREADYEXIST_EXCEPTION_MESSAGE = "Already exist %s => %s";

	public RecordAlreadyExistException(String fieldName, String fieldValue) {
		super(String.format(FIELD_ALREADYEXIST_EXCEPTION_MESSAGE, fieldName, fieldValue));
	}
}
