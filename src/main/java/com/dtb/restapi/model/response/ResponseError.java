package com.dtb.restapi.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseError {
	private String message;
	private List<String> errors;
}
