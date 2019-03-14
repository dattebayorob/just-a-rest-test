package com.dtb.restapi.model.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Converter<E, D> {
	@Autowired
	private ModelMapper map;
	private E entity;
	private D dto;

	public Converter entity(E entity) {
		this.entity = entity;
		return this;
	}

	public Converter dto(D dto) {
		this.dto = dto;
		return this;
	}

	public Converter toDto(Class<D> cls) {
		dto = map.map(entity, cls);
		return this;
	}

	public Converter toEntity(Class<E> cls) {
		entity = map.map(dto, cls);
		return this;
	}

	public Object convert() {
		return entity == null ? dto : entity;
	}

}
