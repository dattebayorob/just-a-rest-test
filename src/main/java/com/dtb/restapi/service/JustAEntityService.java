package com.dtb.restapi.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dtb.restapi.model.dtos.JustAEntityDto;

public interface JustAEntityService {
	
	Page<JustAEntityDto> findAll(Pageable pageable);
	
	Optional<JustAEntityDto> findById(Long id);
	
	Optional<JustAEntityDto> save(JustAEntityDto dto);
	
	Optional<JustAEntityDto> update(JustAEntityDto dto);
	
	void deleteById(Long id);
}
