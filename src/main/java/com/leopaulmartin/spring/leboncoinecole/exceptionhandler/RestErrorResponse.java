package com.leopaulmartin.spring.leboncoinecole.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonRootName("error")
public class RestErrorResponse {
	//General error message about nature of error
	private String message;

	//Specific errors in API request processing
	private List<String> details;

	public RestErrorResponse(String message, List<String> details) {
		super();
		this.message = message;
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "ErrorResponse{" +
				"message='" + message + '\'' +
				", details=" + details +
				'}';
	}
}
