package com.dtb.restapi.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.validation.ConstraintViolationProblemModule;

@Configuration
public class ApiConfig {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer problemObjectMapperModules() {
		return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.modules(new ProblemModule(),
				new ConstraintViolationProblemModule());
	}
}
