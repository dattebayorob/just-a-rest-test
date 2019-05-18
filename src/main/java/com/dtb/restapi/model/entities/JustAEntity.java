package com.dtb.restapi.model.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "entity")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JustAEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name", nullable = false, length = 255)
	private String name;
	@Column(name = "cpf", nullable = false, unique = true)
	private String cpf;
	@Column(name = "rg", nullable = false, unique = true)
	private String rg;
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	@Column(name = "date", nullable = false)
	private LocalDateTime date;
	
	
}
