package com.dtb.restapi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtb.restapi.model.dtos.JustAEntityDto;
import com.dtb.restapi.model.entities.JustAEntity;
import com.dtb.restapi.model.exceptions.ResourceNotFoundException;
import com.dtb.restapi.model.exceptions.messages.ErrorMessages;
import com.dtb.restapi.model.response.Response;
import com.dtb.restapi.model.response.ResponseBuilder;
import com.dtb.restapi.service.JustAEntityService;

@RestController
@RequestMapping("/entities")
@CrossOrigin(value = "*")
public class JustAEntityController {
	@Autowired
	private JustAEntityService service;
	@Autowired
	private ModelMapper modelMapper;

	private static final Log log = LogFactory.getLog(JustAEntityController.class);

	@GetMapping
	public ResponseEntity<Response> findAll(Pageable pageable) {
		log.info("Controller: Returning a response with paginated entities");

		Page<JustAEntity> entities = service.findAll(pageable)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PAGE_NOT_FOUND));

		return ResponseEntity
				.ok(new ResponseBuilder().input(entities).output(JustAEntityDto.class).dto());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Response> findById(@PathVariable("id") Long id) {
		log.info("Controller: Returning a response of a entity dto");

		JustAEntity entity = service.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ENTITY_NOT_FOUND + id));

		return ResponseEntity
				.ok(new ResponseBuilder().input(entity).output(JustAEntityDto.class).dto());
	}

	@PostMapping
	public ResponseEntity<Response> save(@Validated @RequestBody JustAEntityDto dto) {
		log.info("Controller: Persisting a entity and returning a response of its dto");

		JustAEntity entity = modelMapper.map(dto, JustAEntity.class);

		return ResponseEntity.ok(service.save(entity).fold(Response::error,
				e -> new ResponseBuilder().input(e).output(JustAEntityDto.class).dto()));

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateById(@PathVariable("id") Long id,
			@Validated @RequestBody JustAEntityDto dto) {
		log.info("Controller: Updating a entity and returning a response of its dto");

		JustAEntity entity = service.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ENTITY_NOT_FOUND + id));
		dto.setId(id);

		return ResponseEntity
				.ok(service.update(modelMapper.map(dto, JustAEntity.class), entity.getName()).fold(Response::error,
						e -> new ResponseBuilder().input(e).output(JustAEntityDto.class).dto()));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		if (!service.findById(id).isPresent())
			throw new ResourceNotFoundException(ErrorMessages.ENTITY_NOT_FOUND + id);

		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
