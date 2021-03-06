package com.dtb.restapi.utils;

import static java.util.Locale.getDefault;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import io.vavr.control.Try;

@Component
public class MessageUtils {
	@Autowired private MessageSource messageSource;
	
	public List<String> getMessage(List<String> source){
		return source
				.stream()
				.map(message -> Try.of(()-> messageSource
						.getMessage(message, null, getDefault()))
						.getOrElse(message))
				.collect(toList());
	}
}
