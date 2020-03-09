package com.leopaulmartin.spring.leboncoinecole.controllers.exceptions;

import com.leopaulmartin.spring.leboncoinecole.exceptions.ErrorResponse;
import com.leopaulmartin.spring.leboncoinecole.exceptions.RecordAlreadyExistException;
import com.leopaulmartin.spring.leboncoinecole.exceptions.RecordNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CategoryExceptionController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Server Error", details);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.header("Responded", "CategoryExceptionController")
				.header("Method", "handleAllExceptions")
				.body(error);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<Object> handleCategoryNotFoundException(RecordNotFoundException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Record Not Found", details);
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.header("Responded", "CategoryExceptionController")
				.header("Method", "handleCategoryNotFoundException")
				.body(error);
	}

	@ExceptionHandler(RecordAlreadyExistException.class)
	public final ResponseEntity<Object> handleRecordAlreadyExistException(RecordAlreadyExistException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Record Already Exist", details);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.header("Responded", "CategoryExceptionController")
				.header("Method", "handleRecordAlreadyExistException")
				.body(error);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(error.getDefaultMessage());
		}
		ErrorResponse error = new ErrorResponse("Validation Failed", details);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.header("Responded", "CategoryExceptionController")
				.header("Method", "handleMethodArgumentNotValid")
				.body(error);
	}
}
