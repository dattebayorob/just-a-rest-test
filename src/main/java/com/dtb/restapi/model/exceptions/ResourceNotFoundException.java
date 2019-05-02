package com.dtb.restapi.model.exceptions;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AbstractException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4384271621307318096L;
	
	
	public ResourceNotFoundException(Error error) {
		super(HttpStatus.NOT_FOUND, Arrays.asList(error));
	}
	
	public ResourceNotFoundException(HttpStatus httpStatus, List<Error> errors) {
		super(httpStatus, errors);
	}

}
