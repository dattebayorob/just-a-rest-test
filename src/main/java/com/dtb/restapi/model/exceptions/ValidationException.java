package com.dtb.restapi.model.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

public class ValidationException extends AbstractException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ValidationException(List<Error> errors) {
		this(HttpStatus.BAD_REQUEST, errors);
	}
	
	public ValidationException(BindingResult result) {
		this(result.getFieldErrors()
				.stream()
				.map(error -> {
					return new Error(
							error.getField(),
							error.getDefaultMessage());
					})
				.collect(Collectors.toList()));
	}
	
	public ValidationException(HttpStatus httpStatus, List<Error> errors) {
		super(httpStatus, errors);
	}
	

}
