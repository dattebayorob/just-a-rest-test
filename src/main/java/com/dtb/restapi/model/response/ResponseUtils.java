package com.dtb.restapi.model.response;

import static org.springframework.web.util.UriComponentsBuilder.fromPath;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class ResponseUtils {

	public static <T> ResponseEntity<T> ok(T data) {
		return ResponseEntity.ok(data);
	}
	
	public static <T> ResponseEntity<Void> created(T id, String uri){
		return ResponseEntity.created(buildURI(id, uri)).build();
	}
	
	public static <T> ResponseEntity<Void> accepted(T data){
		return ResponseEntity.accepted().build();
	}
	
	public static <T> ResponseEntity<T> notFound(){
		return ResponseEntity.notFound().build();
	}
	
	public static <T> ResponseEntity<T> unprocessableEntity(){
		return ResponseEntity.unprocessableEntity().build();
	}
	
	public static <T> URI buildURI(T id, String uri) {
		return fromPath(uri).buildAndExpand(id).toUri();
	}

	public static ResponseEntity<ResponseError> badRequest(List<String> errors) {
		return ResponseEntity.badRequest().body(new ResponseError("Error validating", errors));
	}

	public static ResponseEntity<ResponseError> badRequest(BindingResult result) {
		List<String> errors = result
								.getAllErrors()
								.stream()
								.map(ObjectError::getDefaultMessage)
								.collect(Collectors.toList());
		return badRequest(errors);
	}
}
