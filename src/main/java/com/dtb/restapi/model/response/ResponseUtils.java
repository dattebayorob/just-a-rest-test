package com.dtb.restapi.model.response;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

public class ResponseUtils{

	public static ResponseEntity<Response> okOrBadRequest(Response response) {
		if(response.isData()) {
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.badRequest().body(response);
	}
	
	public static<T> ResponseEntity<T> okOrNotFound(Optional<T> response){
		return response
				.map(ResponseEntity::ok)
				.orElseGet(ResponseEntity.notFound()::build);
	}
	
	public static ResponseEntity<?> notFound(Response response){
		return ResponseEntity.notFound().build();
	}
	
	public static<T> ResponseEntity<T> ok(T obj){
		return ResponseEntity.ok(obj);
	}
	
}
