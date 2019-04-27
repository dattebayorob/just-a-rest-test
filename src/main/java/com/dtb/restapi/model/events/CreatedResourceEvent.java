package com.dtb.restapi.model.events;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class CreatedResourceEvent extends ApplicationEvent{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5159808692565022687L;
	
	private HttpServletResponse response;
	private Long id;
	
	public CreatedResourceEvent(Object source, HttpServletResponse response, Long id) {
		super(source);
		this.response = response;
		this.id = id;
	}

}
