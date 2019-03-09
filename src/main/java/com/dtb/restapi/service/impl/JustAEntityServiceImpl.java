package com.dtb.restapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dtb.restapi.model.entities.JustAEntity;
import com.dtb.restapi.model.repositories.JustAEntityRepository;
import com.dtb.restapi.service.JustAEntityService;

@Service
public class JustAEntityServiceImpl implements JustAEntityService{
	@Autowired
	private JustAEntityRepository repository;
	private static final Log log = LogFactory.getLog(JustAEntityServiceImpl.class);
	
	@Override
	public Page<JustAEntity> findAll(Pageable pageable){
		log.info("Service: returning all entities paginateds");
		return repository.findAll(pageable);
	}
	
	@Override
	public Optional<JustAEntity> findById(Long id) {
		log.info("Service: returning entity by id: "+id);
		return repository.findById(id);
	}

	@Override
	public List<JustAEntity> findByName(String name) {
		log.info("Service: returning entities by name: "+name);
		return repository.findByName(name);
	}

	@Override
	public List<JustAEntity> findByEnabled(boolean enabled) {
		log.info("Service: returning entities"+(enabled?"enabled":"not enabled"));
		return findByEnabled(enabled);
	}
}
