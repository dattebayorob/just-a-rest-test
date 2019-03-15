package com.dtb.restapi.model.converter.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dtb.restapi.model.converter.Converter;
import com.dtb.restapi.model.response.ResponseData;

@Component
public class GenericConverter<E, D> implements Converter<E, D> {
	@Autowired
	private ModelMapper map;
	private E entity;
	private D dto;

	@Override
	public Converter<E, D> entity(E entity) {
		this.entity = entity;
		return this;
	}

	@Override
	public Converter<E, D> dto(D dto) {
		this.dto = dto;
		return this;
	}

	@Override
	public Converter<E, D> toDto(Class<D> cls) {
		this.dto = map.map(entity, cls);
		return this;
	}

	@Override
	public Converter<E, D> toEntity(Class<E> cls) {
		this.entity = map.map(dto, cls);
		return this;
	}

	@Override
	public Object convert() {
		return entity == null ? dto : entity;
	}

	@Override
	public ResponseData toResponse() {
		return ResponseData.data(entity == null ? dto : entity);
	}

}