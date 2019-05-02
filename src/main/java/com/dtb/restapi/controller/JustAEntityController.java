	package com.dtb.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dtb.restapi.model.dtos.JustAEntityDto;
import com.dtb.restapi.model.exceptions.AbstractException;
import com.dtb.restapi.model.exceptions.ValidationException;
import com.dtb.restapi.model.response.Response;
import com.dtb.restapi.model.response.ResponseUtils;
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
	public ResponseEntity<Response> findById(@PathVariable("id") Long id) {
		log.info("Controller: Returning dto of entity with id {}", id);
		
		try{
			return ResponseUtils.ok(service.findById(id));
		}catch(AbstractException e) {
			return ResponseUtils.exception(e);
		}
	}

	@PostMapping
	public ResponseEntity<Response> save(@Validated @RequestBody JustAEntityDto dto, BindingResult result) {
		log.info("Controller: Persisting entity {}", dto);
				
		try {
			if(result.hasErrors())
				throw new ValidationException(result);

			return ResponseUtils.ok(service.save(dto));
		}catch(AbstractException e) {
			return ResponseUtils.exception(e);
		}
		
		}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateById(@PathVariable("id") Long id,
			@Validated @RequestBody JustAEntityDto dto, BindingResult result) {
		log.info("Controller: Updating entity with id {}", id);
		
		try {
			if(result.hasErrors())
				throw new ValidationException(result);

			dto.setId(id);
			return ResponseUtils.ok(service.update(dto));
		}catch(AbstractException e) {
			return ResponseUtils.exception(e);
		}
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable("id") Long id) {
		log.info("Controller: Delete logical of entity with id {}", id);
		
		service.deleteById(id);
	}
}
