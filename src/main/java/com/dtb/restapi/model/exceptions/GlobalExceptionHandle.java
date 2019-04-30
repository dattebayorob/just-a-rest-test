package com.dtb.restapi.model.exceptions;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dtb.restapi.model.response.Response;

@RestControllerAdvice
public class GlobalExceptionHandle{
	@Autowired
	private MessageSource message;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
		List<Error> errors = generateErrors(ex);
		
		return ResponseEntity
				.badRequest()
				.body(new Response(errors));
	}
	
	@ExceptionHandler(ValidationErrorsException.class)
	public ResponseEntity<Response> handleValidationErrors(ValidationErrorsException ex){
		List<Error> errors = generateMessage(ex.getErrors());
		
		return ResponseEntity
				.badRequest()
				.body(new Response(errors));
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Response> handleResourceNotFound(ResourceNotFoundException ex){
		
		return ResponseEntity
				.notFound()
				.build();
	}
	
	private List<Error> generateErrors(MethodArgumentNotValidException ex) {
		return ex
				.getBindingResult()
					.getAllErrors()
					.stream()
					.map(error -> {
						return new Error(
								error.getObjectName(),
								error.getDefaultMessage());
						})
					.collect(Collectors.toList());
	}
	
	private List<Error> generateMessage(List<Error> errors){
		return errors
				.stream()
				.map(error ->{
					error
						.setMessage(message
								.getMessage(error.getMessage(),
								null,
								Locale.getDefault()));
					return error;
				})
				.collect(Collectors.toList());
	}
}
