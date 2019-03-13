package com.dtb.restapi.model.exceptions;

public class ValidationErrorException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ValidationErrorException(String msg) {
		super(msg);
	}

	

}
