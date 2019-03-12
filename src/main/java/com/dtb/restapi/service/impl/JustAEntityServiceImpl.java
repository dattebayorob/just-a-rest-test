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
	public Optional<Page<JustAEntity>> findAll(Pageable pageable){
		log.info("Service: returning all entities paginateds");
		
		return Optional.of(repository.findAll(pageable)).filter(e -> !e.isEmpty());
	}
	
	@Override
	public Optional<JustAEntity> findById(Long id) {
		log.info("Service: returning entity by id: "+id);
		
		return repository.findById(id);
	}

	@Override
	public Optional<List<JustAEntity>> findByName(String name) {
		log.info("Service: returning entities by name: "+name);
		
		return Optional.ofNullable((repository.findByName(name)));
	}

	@Override
	public JustAEntity save(JustAEntity entity) {
		log.info("Service: persisting a entity: "+entity.toString());
		
		return repository.save(entity);
	}

	@Override
	public void deleteById(Long id) {
		log.info("Service: deleting a entity of id: "+id);
		repository.deleteById(id);
		
	}
}
