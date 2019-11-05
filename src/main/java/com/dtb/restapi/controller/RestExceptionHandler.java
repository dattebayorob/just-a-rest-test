package com.dtb.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dtb.restapi.model.exceptions.ValidationException;
import com.dtb.restapi.model.response.ResponseError;
import com.dtb.restapi.model.response.ResponseUtils;
import com.dtb.restapi.service.MessageService;

@RestControllerAdvice
public class RestExceptionHandler {
	@Autowired MessageService messageService;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseError> handleMethodArgumentNotValidExcpetion(MethodArgumentNotValidException exception) {
		return ResponseUtils.badRequest(exception.getBindingResult());
	}
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ResponseError> handleValidationException(ValidationException exception){
		return ResponseUtils.badRequest(messageService.getMessages(exception.getErrors()));
	}
}
