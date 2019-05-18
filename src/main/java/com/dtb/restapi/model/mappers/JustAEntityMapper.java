package com.dtb.restapi.model.mappers;

import org.mapstruct.Mapper;

import com.dtb.restapi.model.dtos.JustAEntityDto;
import com.dtb.restapi.model.entities.JustAEntity;

@Mapper(componentModel = "spring")
public interface JustAEntityMapper {
	
	
	
	JustAEntity toEntity(JustAEntityDto dto);
	
	JustAEntityDto toDto(JustAEntity entity);
}
