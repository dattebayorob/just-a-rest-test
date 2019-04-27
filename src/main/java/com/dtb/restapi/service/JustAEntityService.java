package com.dtb.restapi.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dtb.restapi.model.dtos.JustAEntityDto;
import com.dtb.restapi.model.entities.JustAEntity;

public interface JustAEntityService {
	
	Page<JustAEntityDto> findAll(Pageable pageable);
	
	JustAEntityDto findById(Long id);
	
	JustAEntityDto save(JustAEntityDto dto, HttpServletResponse response);
	
	JustAEntityDto update(JustAEntityDto dto);
	
	void deleteById(Long id);
}
