package com.dtb.restapi.model.response.impl;

import java.util.List;

import com.dtb.restapi.model.response.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseError implements Response{
	private List<String> errors;
}
