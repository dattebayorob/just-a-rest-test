package com.dtb.restapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import com.dtb.restapi.model.exceptions.ValidationErrors;

@ControllerAdvice
public class WebRestControllerAdvice implements ProblemHandling{

//	@ExceptionHandler(ResourceNotFoundException.class)
//	@ResponseStatus(code = HttpStatus.NOT_FOUND)
//	public Response handleResourceNotFoundExeception(ResourceNotFoundException ex) {
//		return Response.error((new ObjectError("Entity", ex.getMessage())));
//	}
//	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	@Override
//	public ResponseEntity<Problem> handleValidationErrorException(MethodArgumentNotValidException ex, NativeWebRequest request) {
//		Map<String, Object> parameters = new HashMap<>();
//		ex
//			.getBindingResult()
//			.getFieldErrors()
//			.forEach(f -> parameters.put(f.getObjectName(), f.getField()));
//
//        Problem problem = Problem.builder()
//            .withTitle("Method argument not valid")
//            .withStatus(defaultConstraintViolationStatus())
//            .with("error", "error.validation")
//            .with("fieldErrors", parameters)
//            .build();
//        return create(ex, problem, request);	}
	
	@ExceptionHandler(ValidationErrors.class)
	public ResponseEntity<Problem> handleValidationErrors(ValidationErrors ex, NativeWebRequest request) {
		return create(ex, request);
	}
}
