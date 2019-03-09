package com.dtb.restapi.controller;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
import com.dtb.restapi.model.response.Response;
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
		Page<JustAEntity> entities = service.findAll(pageable);
		return ResponseEntity.ok(Response.data(entities.map(entity -> modelMapper.map(entity, JustAEntityDto.class))));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Response> findById(@PathVariable("id") Long id) {
		log.info("Controller: Returning a response of a entity dto");
		Optional<JustAEntity> entity = service.findById(id);
		return entity.isPresent()
				? ResponseEntity.ok(Response.data(modelMapper.map(entity.get(), JustAEntityDto.class)))
				: ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Response> save(@Validated @RequestBody JustAEntityDto dto, BindingResult result) {
		log.info("Controller: Persisting a entity and returning a response of its dto");
		if (result.hasErrors())
			return ResponseEntity.badRequest().body(Response.error(result.getAllErrors()));
		return ResponseEntity.ok(Response
				.data(modelMapper.map(service.save(modelMapper.map(dto, JustAEntity.class)), JustAEntityDto.class)));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateById(@PathVariable("id") Long id, @Validated @RequestBody JustAEntityDto dto,
			BindingResult result) {
		log.info("Controller: Updating a entity and returning a response of its dto");
		Optional<JustAEntity> entity = service.findById(id);
		if (!entity.isPresent())
			return ResponseEntity.notFound().build();
		if (result.hasErrors())
			return ResponseEntity.badRequest().body(Response.error(result.getAllErrors()));
		dto.setId(id);
		modelMapper.map(modelMapper.map(dto, JustAEntity.class), entity.get());
		return ResponseEntity.ok(Response.data(modelMapper.map(service.save(entity.get()), JustAEntityDto.class)));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		if (!service.findById(id).isPresent())
			return ResponseEntity.notFound().build();
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
