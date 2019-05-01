	package com.dtb.restapi.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import com.dtb.restapi.model.exceptions.ValidationErrorsException;
import com.dtb.restapi.model.exceptions.Error;
import com.dtb.restapi.model.response.Response;
import com.dtb.restapi.model.response.ResponseUtils;
import com.dtb.restapi.service.JustAEntityService;

import io.vavr.control.Try;
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
		
		return service
				.findById(id)
				.map(ResponseUtils::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Response> save(@Validated @RequestBody JustAEntityDto dto, BindingResult result) {
		log.info("Controller: Persisting entity {}", dto);
		
		if(result.hasErrors())
			return ResponseUtils.badRequest(convertError(result.getFieldErrors()));
		
		return Try.of(() -> service.save(dto))
				.filter(Optional::isPresent)
				.map(ResponseUtils::ok)
				.recover(ex -> {
					List<Error> errors = ((ValidationErrorsException)ex).getErrors(); 
					return ResponseEntity.badRequest().body(Response.errors(errors));
				})
				.getOrElse(ResponseUtils::unprocessable);
		}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateById(@PathVariable("id") Long id,
			@Validated @RequestBody JustAEntityDto dto, BindingResult result) {
		log.info("Controller: Updating entity with id {}", id);
		
		if(result.hasErrors())
			return ResponseUtils.badRequest(convertError(result.getFieldErrors()));
		
		dto.setId(id);
		
		return Try.of(() -> service.update(dto))
				.filter(Optional::isPresent)
				.map(saved -> ResponseEntity.ok(Response.data(saved)))
				.recover(ex -> {
					List<Error> errors = ((ValidationErrorsException)ex).getErrors(); 
					return ResponseEntity.badRequest().body(Response.errors(errors));
				})
				.getOrElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
		log.info("Controller: Delete logical of entity with id {}", id);
		
		return service.deleteById(id)
				.isPresent()?
				ResponseEntity.noContent().build():
				ResponseEntity.notFound().build();
	}
	
	private List<Error> convertError(List<FieldError> errors){
		return errors
		.stream()
		.map(error -> {
			return new Error(
					error.getField(),
					error.getDefaultMessage());
		})
		.collect(Collectors.toList());
	}
}
