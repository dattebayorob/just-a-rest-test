package com.dtb.restapi.model.converter;

import com.dtb.restapi.model.response.ResponseData;

public interface Converter<E, D> {
	
	public Converter<E, D> entity(E entity);

	public Converter<E, D> dto(D dto);

	public Converter<E, D> toDto(Class<D> cls) ;

	public Converter<E, D> toEntity(Class <E> cls);

	public Object convert();
	
	public ResponseData toResponse();

}
