package com.leopaulmartin.spring.leboncoinecole.exceptionhandler;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.ResourceNotFoundException;

public class RestPreconditions {

	public static <T> T checkNotNull(T resource) {
		if (resource == null) {
			throw new ResourceNotFoundException("Resource not found");
		}
		return resource;
	}
}
