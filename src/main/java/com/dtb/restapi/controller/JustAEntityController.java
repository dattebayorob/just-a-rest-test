package com.dtb.restapi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtb.restapi.model.converter.Converter;
import com.dtb.restapi.model.dtos.JustAEntityDto;
import com.dtb.restapi.model.entities.JustAEntity;
import com.dtb.restapi.model.exceptions.ResourceNotFoundException;
import com.dtb.restapi.model.exceptions.messages.ErrorMessages;
import com.dtb.restapi.model.response.ResponseData;
import com.dtb.restapi.model.response.ResponseError;
import com.dtb.restapi.service.JustAEntityService;

@RestController
@RequestMapping("/entities")
@CrossOrigin(value = "*")
public class JustAEntityController {
	@Autowired
	private JustAEntityService service;
	@Autowired
	private Converter<JustAEntity, JustAEntityDto> converter;

	private static final Log log = LogFactory.getLog(JustAEntityController.class);

	@GetMapping
	public ResponseEntity<?> findAll(Pageable pageable) {
		log.info("Controller: Returning a response with paginated entities");

		Page<JustAEntity> entities = service.findAll(pageable)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PAGE_NOT_FOUND));

		return ResponseEntity
				.ok(new ResponseData(entities.map(e -> converter
						.entity(e)
						.toDto(JustAEntityDto.class)
						.convert()
						)));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity findById(@PathVariable("id") Long id) {
		log.info("Controller: Returning a response of a entity dto");

		JustAEntity entity = service.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ENTITY_NOT_FOUND + id));

		return ResponseEntity.ok(new ResponseData(converter
				.entity(entity)
				.toDto(JustAEntityDto.class)
				.convert()
				));
	}

	@PostMapping
	public ResponseEntity save(@Validated @RequestBody JustAEntityDto dto) {
		log.info("Controller: Persisting a entity and returning a response of its dto");

		JustAEntity entity = (JustAEntity) converter.dto(dto).toEntity(JustAEntity.class).convert();

		return ResponseEntity.ok(service.save(entity).fold(ResponseError::ex,
				e -> new ResponseData(converter
				.entity(e)
				.toDto(JustAEntityDto.class)
				.convert())
		));

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity updateById(@PathVariable("id") Long id, @Validated @RequestBody JustAEntityDto dto) {
		log.info("Controller: Updating a entity and returning a response of its dto");

		JustAEntity entity = service.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ENTITY_NOT_FOUND + id));
		
		dto.setId(id);

		return ResponseEntity.ok(service.update(
				(JustAEntity) converter
					.entity(entity)
					.toEntity(JustAEntity.class)
					.convert()
					, entity.getName()).fold(ResponseError::ex,
				e -> new ResponseData(converter
				.entity(e)
				.toDto(JustAEntityDto.class)
				.convert())
		));
	}
	
	/*
	@DeleteMapping(value = "/{id}")
	public ResponseEntity deleteById(@PathVariable("id") Long id) {
		if (!service.findById(id).isPresent())
			throw new ResourceNotFoundException(ErrorMessages.ENTITY_NOT_FOUND + id);

		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}*/
}
