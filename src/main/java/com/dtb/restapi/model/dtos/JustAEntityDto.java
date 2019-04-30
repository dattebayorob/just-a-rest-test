package com.dtb.restapi.model.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JustAEntityDto {
	private Long id;
	@NotEmpty(message = "{entity.name.notnull}")
	@Size(min = 3, max = 255, message = "Its just a Entity, but needs at lest 3 caracteres!")
	private String name;
	@NotEmpty(message = "{entity.cpf.notnull}")
	private String cpf;
	@NotEmpty(message = "{entity.rg.notnull}")
	private String rg;
}
