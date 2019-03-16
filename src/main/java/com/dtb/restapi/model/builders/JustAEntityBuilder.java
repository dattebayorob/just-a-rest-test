package com.dtb.restapi.model.builders;


import com.dtb.restapi.model.entities.JustAEntity;

public class JustAEntityBuilder {
	private JustAEntity entity;
	
	public JustAEntityBuilder() {
		this.entity = new JustAEntity();
	}
	
	public static JustAEntityBuilder builder() {
		return new JustAEntityBuilder();
	}
	
	public JustAEntityBuilder id(Long id) {
		this.entity.setId(id);
		return this;
	}
	
	public JustAEntityBuilder name(String name) {
		this.entity.setName(name);
		return this;
	}
	
	public JustAEntityBuilder enabled(boolean enabled) {
		this.entity.setEnabled(enabled);
		return this;
	}
	
	public JustAEntity build() {
		return this.entity;
	}
}
