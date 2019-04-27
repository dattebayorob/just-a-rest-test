package com.dtb.restapi.service.impl;

import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dtb.restapi.model.converters.EntityDtoConverter;
import com.dtb.restapi.model.dtos.JustAEntityDto;
import com.dtb.restapi.model.entities.JustAEntity;
import com.dtb.restapi.model.exceptions.ResourceNotFoundException;
import com.dtb.restapi.model.exceptions.ValidationErrorException;
import com.dtb.restapi.model.exceptions.messages.ErrorMessages;
import com.dtb.restapi.model.repositories.JustAEntityRepository;
import com.dtb.restapi.service.JustAEntityService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class JustAEntityServiceImpl implements JustAEntityService {
	@Autowired
	private JustAEntityRepository repository;
	@Autowired
	private EntityDtoConverter converter;

	@Override
	public Page<JustAEntityDto> findAll(Pageable pageable) {
		log.info("Service: returning all entities paginateds");

		return repository
				.findByEnabled(true, pageable)
				.map(converter::toDto);
	}

	@Override
	public JustAEntityDto findById(Long id) {
		log.info("Service: returning entity by id: " + id);

		return repository
				.findById(id)
				.filter(JustAEntity::isEnabled)
				.map(converter::toDto)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ENTITY_NOT_FOUND));
	}

	@Override
	public JustAEntityDto save(JustAEntityDto dto) {
		log.info("Service: persisting a entity: " + dto);
		
		
		if(repository.existsByName(dto.getName()))
			throw new ValidationErrorException(ErrorMessages.ENTITY_NAME_UNIQUE);
		
		return Optional
				.of(converter.toEntity(dto))
				.map(repository::save)
				.map(converter::toDto)
				.get();
	}

	@Override
	public JustAEntityDto update(JustAEntityDto dto) {
		log.info("Service: Updating a entity: " + dto);
		
		Predicate<JustAEntity> isNameUnique = e -> !e.getName().equals(dto.getName())&&repository.existsByName(dto.getName());
		
		return repository
				.findById(dto.getId())
				.filter(JustAEntity::isEnabled)
				.map(entity -> {
					if(isNameUnique.test(entity))
						throw new ValidationErrorException(ErrorMessages.ENTITY_NAME_UNIQUE);
					entity.setName(dto.getName());
					return entity;
					})
				.map(converter::toDto)
				.orElseThrow(() ->  new ResourceNotFoundException(ErrorMessages.ENTITY_NOT_FOUND));
	}
	
	@Override
	public void deleteById(Long id) {
		log.info("Service: deleting a entity of id: " + id);
		
		JustAEntity entity = repository
				.findById(id)
				.filter(JustAEntity::isEnabled)
				.orElseThrow(() ->  new ResourceNotFoundException(ErrorMessages.ENTITY_NOT_FOUND));
		entity.setEnabled(false);
		repository.save(entity);

	}
}
