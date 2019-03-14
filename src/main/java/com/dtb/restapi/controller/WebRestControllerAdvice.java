package com.dtb.restapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dtb.restapi.model.exceptions.ResourceNotFoundException;
import com.dtb.restapi.model.exceptions.ValidationErrorException;
import com.dtb.restapi.model.response.ResponseError;

@RestControllerAdvice
public class WebRestControllerAdvice {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ResponseError handleResourceNotFoundExeception(ResourceNotFoundException ex) {
		return new ResponseError().error(new ObjectError("Entity", ex.getMessage())).build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseError handleValidationErrorException(MethodArgumentNotValidException ex) {
		return new ResponseError().errors(ex.getBindingResult().getAllErrors()).build();
	}

	@ExceptionHandler(ValidationErrorException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseError handleValidationERrorException(ValidationErrorException ex) {
		return new ResponseError().error(new ObjectError("Entity", ex.getMessage())).build();
	}
}
