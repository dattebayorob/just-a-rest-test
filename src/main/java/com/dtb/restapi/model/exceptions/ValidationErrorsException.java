package com.dtb.restapi.model.exceptions;

import java.util.List;

import lombok.Getter;

@Getter
public class ValidationErrorsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final List<Error> errors;
	public ValidationErrorsException(String message, List<Error> errors) {
		super(message);
		this.errors = errors;
	}

}
