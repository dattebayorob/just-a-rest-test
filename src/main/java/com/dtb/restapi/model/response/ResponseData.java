package com.dtb.restapi.model.response;

public class ResponseData implements Response{
	private Object data;

	public ResponseData() {
	}

	public ResponseData(Object data) {
		this.data = data;
	}

	public static ResponseData data(Object data) {
		return new ResponseData(data);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
