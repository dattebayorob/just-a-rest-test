package com.dtb.restapi.model.response.impl;

import com.dtb.restapi.model.response.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseData implements Response{
	private Object data;

	public static ResponseData data(Object data) {
		return new ResponseData(data);
	}

	
}
