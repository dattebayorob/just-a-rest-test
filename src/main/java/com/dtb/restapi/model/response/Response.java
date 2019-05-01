package com.dtb.restapi.model.response;

import java.util.List;

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
}
