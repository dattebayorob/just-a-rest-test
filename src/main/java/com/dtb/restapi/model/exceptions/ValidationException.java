package com.dtb.restapi.model.exceptions;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final List<String> errors;
	
	public ValidationException(String ... errors) {
		this.errors = Arrays.asList(errors);
	}
	
	public ValidationException(List<String> errors) {
		this.errors = errors;
	}
	

}
