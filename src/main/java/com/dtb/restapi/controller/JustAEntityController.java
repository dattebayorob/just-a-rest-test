	package com.dtb.restapi.controller;

import java.util.List;
import java.util.Optional;

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
import com.dtb.restapi.model.exceptions.ValidationException;
import com.dtb.restapi.model.response.Response;
import com.dtb.restapi.model.response.ResponseUtils;
import com.dtb.restapi.service.JustAEntityService;
import com.dtb.restapi.utils.MessageUtils;

import io.vavr.control.Try;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/entities")
@CrossOrigin(value = "*")
@Log4j2
public class JustAEntityController {
	@Autowired
	private JustAEntityService service;
	@Autowired
	private MessageUtils message;

	@GetMapping
	public ResponseEntity<Page<JustAEntityDto>> findAll(Pageable pageable) {
		log.info("Controller: returing page of entities");

		Page<JustAEntityDto> dtos = service.findAll(pageable);

		return ResponseEntity.ok(dtos);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Response> findById(@PathVariable("id") Long id) {
		log.info("Controller: Returning dto of entity with id {}", id);
		
		return service
				.findById(id)
				.map(ResponseUtils::ok)
				.orElseGet(ResponseUtils::notFound);
	}

	@PostMapping
	public ResponseEntity<Response> save(@Validated @RequestBody JustAEntityDto dto, BindingResult result) {
		log.info("Controller: Persisting entity {}", dto);
		
		if(result.hasErrors())
			return ResponseUtils.badRequest(result);
		
		return 
			Try.of(()-> service.save(dto))
			.filter(Optional::isPresent)
			.map(ResponseUtils::ok)
			.recover(ValidationException.class,
					ex -> {
						List<String> errors = message.getMessage(ex.getErrors());
						return ResponseUtils.badRequest(errors);
					})
			.getOrElseGet(ResponseUtils::unprocessableEntity);
	
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateById(@PathVariable("id") Long id,
			@Validated @RequestBody JustAEntityDto dto, BindingResult result) {
		log.info("Controller: Updating entity with id {}", id);
		
		if(result.hasErrors())
			return ResponseUtils.badRequest(result);
		
		dto.setId(id);
		
		return 
			Try.of(()-> service.update(dto))
			.filter(Optional::isPresent)
			.map(ResponseUtils::ok)
			.recover(ValidationException.class,
					ex -> {
						List<String> errors = message.getMessage(ex.getErrors());
						return ResponseUtils.badRequest(errors);
					})
			.getOrElseGet(ResponseUtils::notFound);
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable("id") Long id) {
		log.info("Controller: Delete logical of entity with id {}", id);
		
		service.deleteById(id);
	}
}
