package com.dtb.restapi.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.dtb.restapi.model.dtos.JustAEntityDto;
import com.dtb.restapi.model.entities.JustAEntity;

@Mapper(componentModel = "spring")
public interface JustAEntityMapper {
	
	
	@Mapping(target = "date",
			expression = "java(java.time.LocalDateTime.now())")
	@Mapping(target = "enabled",
			expression = "java(true)")
	JustAEntity toEntity(JustAEntityDto dto);
	
	JustAEntity toEntity(JustAEntityDto dto, @MappingTarget JustAEntity entity);
	
	JustAEntityDto toDto(JustAEntity entity);
}	
