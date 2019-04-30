package com.dtb.restapi.model.response;

import java.util.List;

import com.dtb.restapi.model.exceptions.Error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
	private List<Error> errors;

}
