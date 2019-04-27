package com.dtb.restapi.model.dtos;

import javax.validation.constraints.NotNull;
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
	@NotNull(message = "Its just a Entity, but needs a name!")
	@Size(min = 3, max = 255, message = "Its just a Entity, but needs at lest 3 caracteres!")
	private String name;
}
