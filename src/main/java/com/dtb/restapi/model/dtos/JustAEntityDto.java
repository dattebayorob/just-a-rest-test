package com.dtb.restapi.model.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class JustAEntityDto {
	@NotNull
	private Long id;
	@NotNull(message = "Its just a Entity, but needs a name!")
	@Size(min = 3, max = 255, message = "Its just a Entity, but needs at lest 3 caracteres!")
	private String name;
	@NotNull(message = "Its just a Entity, but needs to be seted as enabled or not!")
	private boolean enabled;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
