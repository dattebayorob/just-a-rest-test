package com.dtb.restapi.service.impl;

import static com.dtb.restapi.model.exceptions.messages.ErrorMessages.ENTITY_CPF_UNIQUE;
import static com.dtb.restapi.model.exceptions.messages.ErrorMessages.ENTITY_NAME_UNIQUE;
import static com.dtb.restapi.model.exceptions.messages.ErrorMessages.ENTITY_NOT_FOUND;
import static com.dtb.restapi.model.exceptions.messages.ErrorMessages.ENTITY_RG_UNIQUE;

import java.util.HashMap;
import java.util.Map;
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
import com.dtb.restapi.model.exceptions.ValidationErrors;
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
	private Map<String, Object> errors;

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
				.orElseThrow(() -> new ResourceNotFoundException(ENTITY_NOT_FOUND));
	}

	@Override
	public JustAEntityDto save(JustAEntityDto dto) {
		log.info("Service: persisting a entity: " + dto);
		
		validate(dto);
		if(!errors.isEmpty())
			throw new ValidationErrors(errors);
		
		return Optional
				.of(converter.toEntity(dto))
				.map(repository::save)
				.map(converter::toDto)
				.get();
	}

	@Override
	public JustAEntityDto update(JustAEntityDto dto) {
		log.info("Service: Updating a entity: " + dto);
		
		return repository
				.findById(dto.getId())
				.filter(JustAEntity::isEnabled)
				.map(entity -> {
					validateUpdate(entity,dto);
					if(!errors.isEmpty())
						throw new ValidationErrors(errors);
					entity.setName(dto.getName());
					entity.setCpf(dto.getCpf());
					entity.setRg(dto.getRg());
					repository.save(entity);
					return entity;
					})
				.map(converter::toDto)
				.orElseThrow(() ->  new ResourceNotFoundException(ENTITY_NOT_FOUND));
	}
	
	@Override
	public void deleteById(Long id) {
		log.info("Service: deleting a entity of id: " + id);
		
		JustAEntity entity = repository
				.findById(id)
				.filter(JustAEntity::isEnabled)
				.orElseThrow(() ->  new ResourceNotFoundException(ENTITY_NOT_FOUND));
		entity.setEnabled(false);
		repository.save(entity);

	}
	private void validate(JustAEntityDto dto){
		errors = new HashMap<>();
		if(repository.existsByName(dto.getName()))
			errors.put("name", ENTITY_NAME_UNIQUE);
		if(repository.existsByCpf(dto.getCpf()))
			errors.put("cpf", ENTITY_CPF_UNIQUE);
		if(repository.existsByRg(dto.getRg()))
			errors.put("rg", ENTITY_RG_UNIQUE);
	}
	
	private void validateUpdate(JustAEntity entity, JustAEntityDto dto){
		errors = new HashMap<>();
		Predicate<JustAEntity> isNameUnique = e -> !e.getName().equals(dto.getName())&&repository.existsByName(dto.getName());
		Predicate<JustAEntity> isCpfUnique = e -> !e.getCpf().equals(dto.getCpf())&&repository.existsByCpf(dto.getCpf());
		Predicate<JustAEntity> isRgUnique = e -> !e.getRg().equals(dto.getRg())&&repository.existsByRg(dto.getRg());
		if(isNameUnique.test(entity))
			errors.put("name", ENTITY_NAME_UNIQUE);
		if(isCpfUnique.test(entity))
			errors.put("cpf", ENTITY_CPF_UNIQUE);
		if(isRgUnique.test(entity))
			errors.put("rg", ENTITY_RG_UNIQUE);
	}
}
