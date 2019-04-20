package com.dtb.restapi.model.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dtb.restapi.model.dtos.JustAEntityDto;
import com.dtb.restapi.model.entities.JustAEntity;

@Component
public class EntityDtoConverter{
	@Autowired
	private ModelMapper mapper;

	public JustAEntity toEntity(JustAEntityDto dto) {
		return mapper.map(dto, JustAEntity.class);
	}
	public JustAEntity toEntity(JustAEntityDto dto, JustAEntity source) {
		dto.setId(source.getId());
		mapper.map(dto, source);
		return source;
		
	}
	public JustAEntityDto toDto(JustAEntity entity){
		return mapper.map(entity, JustAEntityDto.class);
	}
	
	
}
