package com.cdd.sangchupassport.exception;

import com.cdd.sangchupassport.Passport;

import lombok.Getter;

@Getter
public class PassportException extends RuntimeException {

	private final String passportId;
	private final int memberId;

	private final int statusCode;
	private final String errorCode;
	private final String message;

	public PassportException(
		final Passport passport,
		final PassportErrorCode passportErrorCode
	) {
		passportId = passport.getId();
		memberId = passport.getMemberId();
		errorCode = passportErrorCode.getErrorCode();
		statusCode = passportErrorCode.getStatusCode();
		message = passportErrorCode.getMessage();
	}
}
