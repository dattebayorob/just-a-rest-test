package com.dtb.restapi.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dtb.restapi.model.entities.JustAEntity;

import io.vavr.control.Either;

public interface JustAEntityService {
	
	Optional<Page<JustAEntity>> findAll(Pageable pageable);
	
	Optional<JustAEntity> findById(Long id);
	
	Either<RuntimeException, JustAEntity> save(JustAEntity entity);
	
	Either<RuntimeException, JustAEntity> update(JustAEntity entity, String name);
	
	void deleteById(Long id);
}
