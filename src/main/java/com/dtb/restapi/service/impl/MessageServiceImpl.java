package com.dtb.restapi.service.impl;

import static java.util.Locale.getDefault;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.dtb.restapi.service.MessageService;

import io.vavr.control.Try;

@Service
public class MessageServiceImpl implements MessageService{
	@Autowired private MessageSource messageSource;
	
	public List<String> getMessages(List<String> source){
		return source
				.stream()
				.map(message -> Try.of(()-> messageSource
						.getMessage(message, null, getDefault()))
						.getOrElse(message))
				.collect(toList());
	}

}
