package com.dtb.restapi.model.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dtb.restapi.model.entities.JustAEntity;

public interface JustAEntityRepository extends JpaRepository<JustAEntity, Long> {
	Page<JustAEntity> findAll(Pageable pageable);

	Optional<JustAEntity> findById(Long id);

	List<JustAEntity> findByName(String name);

	List<JustAEntity> findByEnabled(boolean enabled);

	boolean existsByName(String name);
}
