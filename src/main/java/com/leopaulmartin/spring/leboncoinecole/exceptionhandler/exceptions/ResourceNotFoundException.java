package com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final String RESOURCE_NOTFOUND_EXCEPTION_MESSAGE = "Resource Not Found => %s";

	public ResourceNotFoundException(String resourceName) {
		super(String.format(RESOURCE_NOTFOUND_EXCEPTION_MESSAGE, resourceName));
	}
}

