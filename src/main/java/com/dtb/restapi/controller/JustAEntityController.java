	package com.dtb.restapi.controller;

import javax.servlet.http.HttpServletResponse;

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
import com.dtb.restapi.service.JustAEntityService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/entities")
@CrossOrigin(value = "*")
@Log4j2
public class JustAEntityController {
	@Autowired
	private JustAEntityService service;

	@GetMapping
	public ResponseEntity<Page<JustAEntityDto>> findAll(Pageable pageable) {
		log.info("Controller: returing page of entities");

		Page<JustAEntityDto> dtos = service.findAll(pageable);

		return ResponseEntity.ok(dtos);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<JustAEntityDto> findById(@PathVariable("id") Long id) {
		log.info("Controller: Returning dto of entity with id {}", id);

		JustAEntityDto dto = service.findById(id);
		
		return ResponseEntity.ok(dto);
	}

	@PostMapping
	public ResponseEntity<JustAEntityDto> save(@Validated @RequestBody JustAEntityDto dto, HttpServletResponse response) {
		log.info("Controller: Persisting entity {}", dto);

		dto = service.save(dto,response);
		
		

		return ResponseEntity.ok(dto);

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<JustAEntityDto> updateById(@PathVariable("id") Long id,
			@Validated @RequestBody JustAEntityDto dto) {
		log.info("Controller: Updating entity with id {}", id);
		
		dto.setId(id);
		dto = service.update(dto);
		
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable("id") Long id) {
		log.info("Controller: Delete logical of entity with id {}", id);
		
		service.deleteById(id);
	}
}
