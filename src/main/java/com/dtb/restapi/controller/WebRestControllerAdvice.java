package com.dtb.restapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dtb.restapi.model.exceptions.ResourceNotFoundException;
import com.dtb.restapi.model.exceptions.ValidationErrorException;
import com.dtb.restapi.model.response.Response;

@RestControllerAdvice
public class WebRestControllerAdvice {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Response handleResourceNotFoundExeception(ResourceNotFoundException ex) {
		return Response.error((new ObjectError("Entity", ex.getMessage())));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Response handleValidationErrorException(MethodArgumentNotValidException ex) {
		return Response.error(ex.getBindingResult().getAllErrors());
	}
	
	@ExceptionHandler(ValidationErrorException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Response handleValidationERrorException(ValidationErrorException ex) {
		return Response.error(new ObjectError("Entity", ex.getMessage()));
	}
}
