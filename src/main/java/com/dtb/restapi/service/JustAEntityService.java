package com.dtb.restapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dtb.restapi.model.dtos.JustAEntityDto;
import com.dtb.restapi.model.exceptions.Error;

import io.vavr.control.Either;

public interface JustAEntityService {
	
	Page<JustAEntityDto> findAll(Pageable pageable);
	
	Optional<JustAEntityDto> findById(Long id);
	
	Either<List<Error>, JustAEntityDto> save(JustAEntityDto dto);
	
	Either<List<Error>, JustAEntityDto> update(JustAEntityDto dto);
	
	void deleteById(Long id);
}
