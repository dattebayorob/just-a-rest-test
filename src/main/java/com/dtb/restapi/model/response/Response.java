package com.dtb.restapi.model.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.ObjectError;

public class Response {
	private List<String> error;

	public Response() {
	}

	public Response(List<String> error) {
		this.error = error;
	}
	
	public static Response error(ObjectError error) {
		List<String> e = new ArrayList<>();
		e.add(error.getDefaultMessage());
		return new Response(e);
	}
	public static Response error(List<ObjectError> errors) {
		List<String> e = new ArrayList<>();
		errors.forEach(error -> e.add(error.getDefaultMessage()));
		return new Response(e);

	}
	
	public static Response error(RuntimeException e) {
		throw e;
	}

	public List<String> getError() {
		return this.error == null ? new ArrayList<>() : this.error;
	}

	public void setError(List<String> error) {
		this.error = error;
	}

}
