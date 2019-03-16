package com.dtb.restapi.model.converter;

public interface Converter<E,D> {
	
	public Convert<E, D> toDto(Class<D> cls);
	
	public Convert<D, E> toEntity(Class<E> cls);
	
}
