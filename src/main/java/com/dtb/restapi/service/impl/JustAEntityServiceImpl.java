package com.dtb.restapi.service.impl;

import static com.dtb.restapi.model.exceptions.messages.ErrorMessages.ENTITY_NOT_FOUND;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.dtb.restapi.model.converters.EntityDtoConverter;
import com.dtb.restapi.model.dtos.JustAEntityDto;
import com.dtb.restapi.model.entities.JustAEntity;
import com.dtb.restapi.model.exceptions.Error;
import com.dtb.restapi.model.repositories.JustAEntityRepository;
import com.dtb.restapi.service.JustAEntityService;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class JustAEntityServiceImpl implements JustAEntityService {
	@Autowired
	private JustAEntityRepository repository;
	@Autowired
	private EntityDtoConverter converter;
	
	private List<Error> errors;

	@Override
	public Page<JustAEntityDto> findAll(Pageable pageable) {
		log.info("Service: returning all entities paginateds");

		return repository
				.findByEnabled(true, pageable)
				.map(converter::toDto);
	}

	@Override
	public Optional<JustAEntityDto> findById(Long id) {
		log.info("Service: returning entity by id: " + id);

		return repository
				.findById(id)
				.filter(JustAEntity::isEnabled)
				.map(converter::toDto);
	}

	@Override
	public Either<List<Error>, JustAEntityDto> save(JustAEntityDto dto) {
		log.info("Service: persisting a entity: " + dto);
		
		if(validate(dto))
			return Either.left(errors);
		
		return Either.right(
				Optional
				.ofNullable(converter.toEntity(dto))
				.map(repository::save)
				.map(converter::toDto)
				.orElseThrow(() -> new RuntimeException("lala")));
	}

	@Override
	public Either<List<Error>, JustAEntityDto> update(JustAEntityDto dto) {
		log.info("Service: Updating a entity: " + dto);
		Optional<JustAEntity> optional = repository.findById(dto.getId()).filter(JustAEntity::isEnabled);
		
		if(!optional.isPresent())
			throw new ResourceNotFoundException();
		
		if(validateUpdate(optional.get(), dto))
			return Either.left(errors);
		
		return Either.right(
				optional
				.map(entity -> {
					entity.setName(dto.getName());
					entity.setCpf(dto.getCpf());
					entity.setRg(dto.getRg());
					repository.save(entity);
					return entity;
				})
				.map(converter::toDto)
				.get());
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
	
	private Boolean validate(JustAEntityDto dto){
		errors = new LinkedList<>();
		if(repository.existsByName(dto.getName()))
			errors.add(new Error("name", "entity.name.unique"));
		if(repository.existsByCpf(dto.getCpf()))
			errors.add(new Error("cpf", "entity.cpf.unique"));
		if(repository.existsByRg(dto.getRg()))
			errors.add(new Error("rg", "entity.rg.unique"));
		
		return !errors.isEmpty();
	}
	
	private Boolean validateUpdate(JustAEntity entity, JustAEntityDto dto){
		errors = new LinkedList<>();
		Predicate<JustAEntity> isNameUnique = e -> !e.getName().equals(dto.getName())&&repository.existsByName(dto.getName());
		Predicate<JustAEntity> isCpfUnique = e -> !e.getCpf().equals(dto.getCpf())&&repository.existsByCpf(dto.getCpf());
		Predicate<JustAEntity> isRgUnique = e -> !e.getRg().equals(dto.getRg())&&repository.existsByRg(dto.getRg());
		
		if(isNameUnique.test(entity))
			errors.add(new Error("name", "entity.name.unique"));
		if(isCpfUnique.test(entity))
			errors.add(new Error("cpf", "entity.cpf.unique"));
		if(isRgUnique.test(entity))
			errors.add(new Error("rg", "entity.rg.unique"));
		
		return !errors.isEmpty();
	}
}
