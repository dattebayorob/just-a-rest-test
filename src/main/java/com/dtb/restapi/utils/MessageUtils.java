package com.dtb.restapi.utils;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MessageUtils {
	@Autowired
	private MessageSource messageSource;
	
	public List<String> getMessage(List<String> source){
		return 
			source
			.stream()
			.map(message -> {
				return
				Try.of(()->messageSource
					.getMessage(message, null, Locale.getDefault()))
					.getOrElse(message);
				})
			.collect(Collectors.toList());
	}
}
