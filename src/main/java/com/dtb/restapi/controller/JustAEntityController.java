package com.dtb.restapi.controller;

import static com.dtb.restapi.config.EndPoints.ENTITIES;
import static org.springframework.http.ResponseEntity.notFound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dtb.restapi.model.dtos.JustAEntityDto;
import com.dtb.restapi.model.response.ResponseUtils;
import com.dtb.restapi.service.JustAEntityService;

@RestController
@RequestMapping("/entities")
@CrossOrigin(allowCredentials = "true", origins = "*")
public class JustAEntityController {
	@Autowired private JustAEntityService service;

	@GetMapping
	public ResponseEntity<Page<JustAEntityDto>> findAll(Pageable pageable) {

		Page<JustAEntityDto> dtos = service.findAll(pageable);
		return ResponseEntity.ok(dtos);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<JustAEntityDto> findById(@PathVariable("id") Long id) {
		
		return service
				.findById(id)
				.map(ResponseEntity::ok)
				.orElseGet(notFound()::build);
	}

	@PostMapping
	public ResponseEntity<Void> save(@Validated @RequestBody JustAEntityDto dto) {
		
		return service.save(dto)
				.map(savedDto -> ResponseUtils.created(savedDto.getId(), ENTITIES+"/{id}"))
				.orElseGet(ResponseUtils::unprocessableEntity);
	
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> updateById(
			@PathVariable("id") Long id,
			@Validated @RequestBody JustAEntityDto dto) {
		
		dto.setId(id);
		return service.update(dto)
				.map(ResponseUtils::accepted)
				.orElseGet(ResponseUtils::notFound);
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable("id") Long id) {
		service.deleteById(id);
	}
}
