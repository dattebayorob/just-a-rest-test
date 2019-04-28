package com.dtb.restapi.model.exceptions.messages;

public abstract class ErrorMessages {
	public static final String ENTITY_NOT_FOUND = "There is no entity for the id";
	public static final String PAGE_NOT_FOUND = "There is no entities for this filter.";
	public static final String ENTITY_NAME_UNIQUE = "Its Just a Entity, but the name needs to be unique.";
	public static final String ENTITY_CPF_UNIQUE = "Its Just a Entity, but the cpf needs to be unique.";
	public static final String ENTITY_RG_UNIQUE = "Its Just a Entity, but the rg needs to be unique.";
}
