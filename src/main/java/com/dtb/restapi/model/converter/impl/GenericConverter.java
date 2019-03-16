package com.dtb.restapi.model.converter.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dtb.restapi.model.converter.Converter;

@Component
public class GenericConverter<E, D>{
	@Autowired
	private ModelMapper map;

	public Converter<E, D> toDto(Class<D> cls){
		return dto -> map.map(dto, cls);
	}
	
	public Converter<D, E> toEntity(Class<E> cls){
		return entity -> map.map(entity, cls);
	}
	
	
}