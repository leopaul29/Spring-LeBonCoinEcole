package com.leopaulmartin.spring.leboncoinecole.exceptionhandler;

import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordAlreadyExistException;
import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordIdMismatchException;
import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.RecordNotFoundException;
import com.leopaulmartin.spring.leboncoinecole.exceptionhandler.exceptions.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		RestErrorResponse error = new RestErrorResponse("Server Error", details);
		logger.error(error.toString());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.header("Method", "handleAllExceptions")
				.body(error);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		RestErrorResponse error = new RestErrorResponse("Resource Not Found", details);
		logger.error(error.toString());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.header("Method", "handleResourceNotFoundException")
				.body(error);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		RestErrorResponse error = new RestErrorResponse("Record Not Found", details);
		logger.error(error.toString());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.header("Method", "handleRecordNotFoundException")
				.body(error);
	}

	@ExceptionHandler(RecordAlreadyExistException.class)
	public final ResponseEntity<Object> handleRecordAlreadyExistException(RecordAlreadyExistException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		RestErrorResponse error = new RestErrorResponse("Record Already Exist", details);
		logger.error(error.toString());
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
				.header("Method", "handleRecordAlreadyExistException")
				.body(error);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		RestErrorResponse error = new RestErrorResponse("Access Denied", details);
		logger.error(error.toString());
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.header("Method", "handleAccessDeniedException")
				.body(error);
	}

//	@ExceptionHandler({RecordIdMismatchException.class, ConstraintViolationException.class, DataIntegrityViolationException.class})
//	public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
//		return handleExceptionInternal(ex, ex.getLocalizedMessage(),
//				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//	}

	@ExceptionHandler({RecordIdMismatchException.class, ConstraintViolationException.class, DataIntegrityViolationException.class})
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleTypeMismatch(ex,
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(error.getDefaultMessage());
		}
		RestErrorResponse error = new RestErrorResponse("Validation Failed", details);
		logger.error(error.toString());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.header("Method", "handleMethodArgumentNotValid")
				.body(error);
	}
}