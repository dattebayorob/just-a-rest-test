package com.dtb.restapi.model.response;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.FieldError;

import com.dtb.restapi.model.exceptions.Error;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Response {
	private List<Error> errors;
	private Object data;

	public Response(List<Error> errors) {
		this.errors = errors;
	}

	public Response(Object data) {
		this.data = data;
	}

	public static Response data(Object data) {
		return new Response(data);
	}

	public static Response errors(List<Error> errors) {
		return new Response(errors);
	}

	public static Response validation(List<FieldError> errors) {
		return new Response(errors
				.stream()
				.map(error -> {
					return new Error(
							error.getField(),
							error.getDefaultMessage());
				})
				.collect(Collectors.toList()));
	}
	
	public static Response exception(RuntimeException e) {
		throw e;
	}

	public Boolean isData() {
		if(errors == null)
			return true;
		return errors.isEmpty();
	}
}
