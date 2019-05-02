package com.dtb.restapi.model.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class AbstractException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5375339877339180458L;
	
	private HttpStatus httpStatus;
	private List<Error> errors;
	
	public AbstractException(HttpStatus httpStatus, List<Error> errors) {
		this.httpStatus = httpStatus;
		this.errors = errors;
	}
}
