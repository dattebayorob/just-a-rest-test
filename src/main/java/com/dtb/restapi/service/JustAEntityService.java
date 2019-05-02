package com.dtb.restapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dtb.restapi.model.dtos.JustAEntityDto;

public interface JustAEntityService {
	
	Page<JustAEntityDto> findAll(Pageable pageable);
	
	JustAEntityDto findById(Long id);
	
	JustAEntityDto save(JustAEntityDto dto);
	
	JustAEntityDto update(JustAEntityDto dto);
	
	void deleteById(Long id);
}
