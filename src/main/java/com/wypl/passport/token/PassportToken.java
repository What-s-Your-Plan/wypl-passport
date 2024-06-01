package com.wypl.passport.token;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(timeToLive = 5)
public class PassportToken {
	@Id
	public String passportId;

	private PassportToken(
		final String passportId
	) {
		this.passportId = passportId;
	}

	public static PassportToken from(
		final String passportId
	) {
		return new PassportToken(passportId);
	}
}