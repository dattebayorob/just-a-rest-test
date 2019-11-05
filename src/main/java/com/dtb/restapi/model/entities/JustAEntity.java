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
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "entity")
@Builder
@Getter
@Setter
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
	@Default
	private boolean enabled = true;
	@Column(name = "date", nullable = false)
	@Default
	private LocalDateTime date = LocalDateTime.now();	
	
}
