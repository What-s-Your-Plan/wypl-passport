package com.cdd.sangchupassport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cdd.sangchupassport.exception.PassportAdvice;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class BeanConfiguration {
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public PassportAdvice passportAdvice() {
		return new PassportAdvice();
	}
}
