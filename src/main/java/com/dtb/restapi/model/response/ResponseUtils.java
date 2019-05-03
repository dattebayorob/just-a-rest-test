package com.dtb.restapi.model.response;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.dtb.restapi.model.response.impl.ResponseData;
import com.dtb.restapi.model.response.impl.ResponseError;

public class ResponseUtils {

	public static <T> ResponseEntity<Response> ok(T data) {
		return ResponseEntity.ok(ResponseData.data(data));
	}
	
	public static <T> ResponseEntity<Response> notFound(){
		return ResponseEntity.notFound().build();
	}
	
	public static <T> ResponseEntity<Response> unprocessableEntity(){
		return ResponseEntity.unprocessableEntity().build();
	}

	public static ResponseEntity<Response> badRequest(List<String> errors) {
		return ResponseEntity.badRequest().body(new ResponseError(errors));
	}

	public static ResponseEntity<Response> badRequest(BindingResult result) {
		List<String> e = 
				result
				.getAllErrors()
				.stream()
				.map(ObjectError::getDefaultMessage)
				.collect(Collectors.toList());
		return ResponseEntity.badRequest().body(new ResponseError(e));		
	}
}
