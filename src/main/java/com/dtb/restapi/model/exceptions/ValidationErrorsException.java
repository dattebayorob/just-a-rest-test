package com.dtb.restapi.model.exceptions;

import java.util.Map;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ValidationErrors extends AbstractThrowableProblem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ValidationErrors(Map<String, Object> parameters) {
		super(null, null, Status.BAD_REQUEST, null, null, null, parameters);
	}

}
