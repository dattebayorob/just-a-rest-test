package com.dtb.restapi.model.response;

import org.springframework.http.ResponseEntity;

import com.dtb.restapi.model.exceptions.AbstractException;
import com.dtb.restapi.model.response.impl.ResponseData;
import com.dtb.restapi.model.response.impl.ResponseError;

public class ResponseUtils{
	
	public static<T> ResponseEntity<Response> ok(T data){
		return ResponseEntity.ok(ResponseData.data(data));
	}
	
	public static<T extends AbstractException> ResponseEntity<Response> exception(T ex){
		return new ResponseEntity<Response>(ResponseError.errors(ex.getErrors()), ex.getHttpStatus());
	}
}
