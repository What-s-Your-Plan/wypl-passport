package com.cdd.sangchupassport.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.cdd.sangchupassport.Passport;
import com.cdd.sangchupassport.exception.PassportErrorCode;
import com.cdd.sangchupassport.exception.PassportException;
import com.cdd.sangchupassport.token.PassportToken;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PassportValidator {
	private final CrudRepository<PassportToken, String> repository;
	private final ObjectMapper objectMapper;

	public PassportValidator(
			CrudRepository<PassportToken, String> repository,
			@Autowired ObjectMapper mapper
	) {
		this.repository = repository;
		objectMapper = mapper;
	}

	public void validatePassport(final Passport passport) {
		validateExpiredTime(passport);
		validateReuse(passport);
	}

	private void validateExpiredTime(final Passport passport) {
		long now = System.currentTimeMillis();
		if (now > passport.getExpiredTime()) {
			throw new PassportException(passport, PassportErrorCode.EXPIRED_PASSPORT);
		}
	}

	private void validateReuse(final Passport passport) {
		if (repository.existsById(passport.getId())) {
			throw new PassportException(passport, PassportErrorCode.REUSE_PASSPORT);
		}
	}
}
