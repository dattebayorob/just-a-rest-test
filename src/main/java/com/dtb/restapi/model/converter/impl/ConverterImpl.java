package com.dtb.restapi.model.converter.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dtb.restapi.model.converter.Convert;
import com.dtb.restapi.model.converter.Converter;

@Component
public class ConverterImpl<E, D> implements Converter<E, D>{
	@Autowired
	private ModelMapper map;
	
	@Override
	public Convert<E,D> toDto(Class<D> cls) {
		return dto -> map.map(dto, cls);
	}

	@Override
	public Convert<D,E> toEntity(Class<E> cls) {
		return entity -> map.map(entity, cls);
	}
	
	
}