package com.dtb.restapi.model.response;

public interface Response {
	
	default Response toResponse( Object obj) {
		return ResponseData.data(obj);
	}
}
