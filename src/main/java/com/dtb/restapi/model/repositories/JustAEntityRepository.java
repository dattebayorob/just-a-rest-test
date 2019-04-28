package com.dtb.restapi.model.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dtb.restapi.model.entities.JustAEntity;

public interface JustAEntityRepository extends JpaRepository<JustAEntity, Long> {

	Optional<JustAEntity> findById(Long id);

	List<JustAEntity> findByName(String name);

	Page<JustAEntity> findByEnabled(boolean enabled,Pageable pageable);

	boolean existsByName(String name);
	boolean existsByCpf(String cpf);
	boolean existsByRg(String rg);
}
