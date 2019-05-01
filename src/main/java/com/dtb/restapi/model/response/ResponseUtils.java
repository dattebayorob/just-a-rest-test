package com.dtb.restapi.model.response;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dtb.restapi.model.exceptions.ValidationErrorsException;
import com.dtb.restapi.model.exceptions.Error;

public class ResponseUtils{
	
	public static<T> ResponseEntity<Response> ok(T obj){
		return ResponseEntity.ok(Response.data(obj));
	}
	
	public static<T> ResponseEntity<Response> badRequest(List<Error> errors){
		return ResponseEntity.badRequest().body(Response.errors(errors));
	}
	
	public static<T> ResponseEntity<T> notFound(T obj){
		return ResponseEntity.notFound().build();
	}
	
	public static<T> ResponseEntity<T> unprocessable(){
		return ResponseEntity.unprocessableEntity().build();
	}
	
	public static ResponseEntity<Response> ex(ValidationErrorsException ex){
		return ResponseEntity.badRequest().body(Response.errors(ex.getErrors()));
	}
}
