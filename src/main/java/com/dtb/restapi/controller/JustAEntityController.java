package com.dtb.restapi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtb.restapi.model.dtos.JustAEntityDto;
import com.dtb.restapi.model.entities.JustAEntity;
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
	public ResponseEntity<Page<JustAEntityDto>> findAll(Pageable pageable) {
		log.info("Controller: Return a response with paginated entities");
		Page<JustAEntity> entities = service.findAll(pageable);
		return ResponseEntity.ok(entities.map(entity -> modelMapper.map(entities, JustAEntityDto.class)));
	}
}
