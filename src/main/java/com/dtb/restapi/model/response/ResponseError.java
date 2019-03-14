package com.dtb.restapi.model.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.ObjectError;

public class ResponseError implements Response{
	private List<String> errors = new ArrayList<>();
	
	public ResponseError() {
		// TODO Auto-generated constructor stub
	}
	
	public ResponseError(List<String> errors) {
		this.errors = errors;
	}
	
	public ResponseError error(ObjectError error) {
		this.errors.add(error.getDefaultMessage());
		return this;
	}
	
	public static ResponseError ex(RuntimeException ex) {
		throw ex;
	}

	public ResponseError errors(List<ObjectError> errors) {
		errors.forEach(error -> this.errors.add(error.getDefaultMessage()));
		return this;
	}
	
	public ResponseError build() {
		return new ResponseError(errors);
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	
}
