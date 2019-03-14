package com.dtb.restapi.model.response;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public class ResponseBuilder {

	private ModelMapper mapper = new ModelMapper();

	private Object entity;
	private Page<Object> pageEntities;
	private List<Object> listEntities;
	private Object dto;

	public ResponseBuilder input(Object e) {
		entity = e;
		return this;
	}

	public ResponseBuilder input(List<Object> e) {
		listEntities = e;
		return this;
	}

	public ResponseBuilder input(Page<Object> e) {
		pageEntities = e;
		return this;
	}

	public ResponseBuilder output(Class<?> cls) {
		if (entity != null) {
			dto = mapper.map(entity, cls);
		} else if (listEntities != null) {
			List<Object> dtos = new ArrayList<>();
			listEntities.forEach(e -> dtos.add(mapper.map(e, cls)));
			dto = dtos;
		} else {
			Page<Object> dtos = pageEntities.map(e -> mapper.map(e, cls));
			dto = dtos;
		}
		return this;
	}

	public Response entity() {
		return Response.data(entity);
	}

	public Response dto() {
		return Response.data(dto);
	}
}
