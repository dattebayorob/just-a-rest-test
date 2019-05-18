package com.dtb.restapi.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dtb.restapi.model.dtos.JustAEntityDto;
import com.dtb.restapi.model.entities.JustAEntity;
import com.dtb.restapi.model.exceptions.ValidationException;
import com.dtb.restapi.model.mappers.JustAEntityMapper;
import com.dtb.restapi.model.repositories.JustAEntityRepository;
import com.dtb.restapi.service.JustAEntityService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class JustAEntityServiceImpl implements JustAEntityService {
	@Autowired
	private JustAEntityRepository repository;
	@Autowired
	private JustAEntityMapper mapper;
	
	@Override
	public Page<JustAEntityDto> findAll(Pageable pageable) {
		log.info("Service: returning all entities paginateds");

		return repository
				.findByEnabled(true, pageable)
				.map(mapper::toDto);
	}

	@Override
	public Optional<JustAEntityDto> findById(Long id) {
		log.info("Service: returning entity by id: " + id);

		return repository
				.findById(id)
				.filter(JustAEntity::isEnabled)
				.map(mapper::toDto);
	}

	@Override
	public Optional<JustAEntityDto> save(JustAEntityDto dto){
		log.info("Service: persisting a entity: " + dto);
		
		validate(dto);
		
		return Optional
				.ofNullable(mapper.toEntity(dto))
				.map(repository::save)
				.map(mapper::toDto);
	}

	@Override
	public Optional<JustAEntityDto> update(JustAEntityDto dto) {
		log.info("Service: Updating a entity: " + dto);
		
		return repository.findById(dto.getId())
				.filter(JustAEntity::isEnabled)
				.map(entity -> {
					validateUpdate(entity, dto);
					return mapper
						.toEntity(dto, entity);
				})
				.map(repository::save)
				.map(mapper::toDto);
	}
	
	@Override
	public void deleteById(Long id) {
		log.info("Service: deleting a entity of id: " + id);
		repository
			.findById(id)
			.ifPresent(entity -> {
				entity.setEnabled(false);
				repository.save(entity);
			});
	}
	
	private void validate(JustAEntityDto dto){
		List<String> errors = new LinkedList<>();
		if(repository.existsByName(dto.getName()))
			errors.add("entity.name.unique");
		if(repository.existsByCpf(dto.getCpf()))
			errors.add("entity.cpf.unique");
		if(repository.existsByRg(dto.getRg()))
			errors.add("entity.rg.unique");
		if(!errors.isEmpty())
			throw new ValidationException(errors);
	}
	
	private void validateUpdate(JustAEntity entity, JustAEntityDto dto){
		List<String> errors = new LinkedList<>();
		Predicate<JustAEntity> isNameUnique = e -> !e.getName().equals(dto.getName())&&repository.existsByName(dto.getName());
		Predicate<JustAEntity> isCpfUnique = e -> !e.getCpf().equals(dto.getCpf())&&repository.existsByCpf(dto.getCpf());
		Predicate<JustAEntity> isRgUnique = e -> !e.getRg().equals(dto.getRg())&&repository.existsByRg(dto.getRg());
		
		if(isNameUnique.test(entity))
			errors.add("entity.name.unique");
		if(isCpfUnique.test(entity))
			errors.add("entity.cpf.unique");
		if(isRgUnique.test(entity))
			errors.add("entity.rg.unique");
		
		if(!errors.isEmpty())
			throw new ValidationException(errors);
	}
}
