package com.dtb.restapi.service.impl;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dtb.restapi.model.entities.JustAEntity;
import com.dtb.restapi.model.exceptions.ValidationErrorException;
import com.dtb.restapi.model.exceptions.messages.ErrorMessages;
import com.dtb.restapi.model.repositories.JustAEntityRepository;
import com.dtb.restapi.service.JustAEntityService;

import io.vavr.control.Either;

@Service
public class JustAEntityServiceImpl implements JustAEntityService {
	@Autowired
	private JustAEntityRepository repository;
	private static final Log log = LogFactory.getLog(JustAEntityServiceImpl.class);

	@Override
	public Optional<Page<JustAEntity>> findAll(Pageable pageable) {
		log.info("Service: returning all entities paginateds");

		return Optional.of(repository.findAll(pageable)).filter(e -> !e.isEmpty());
	}

	@Override
	public Optional<JustAEntity> findById(Long id) {
		log.info("Service: returning entity by id: " + id);

		return repository.findById(id);
	}

	@Override
	public Either<RuntimeException, JustAEntity> save(JustAEntity entity) {
		log.info("Service: persisting a entity: " + entity.toString());

		if (repository.existsByName(entity.getName()))
			return Either.left(new ValidationErrorException(ErrorMessages.ENTITY_NAME_UNIQUE));
		
		return Either.right(repository.save(entity));
	}

	@Override
	public Either<RuntimeException, JustAEntity> update(JustAEntity entity, String name) {
		log.info("Service: Updating a entity: " + entity.toString());

		if ( ! entity.getName().equals(name) && repository.existsByName(entity.getName()))
			return Either.left(new ValidationErrorException(ErrorMessages.ENTITY_NAME_UNIQUE));
		
		return Either.right(repository.save(entity));
	}
	
	@Override
	public void deleteById(Long id) {
		log.info("Service: deleting a entity of id: " + id);
		
		repository.deleteById(id);

	}
}
