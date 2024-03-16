package com.cdd.sangchupassport.token;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(timeToLive = 5)
public class PassportToken {
    @Id
    public String passportId;

    public PassportToken(String passportId) {
        this.passportId = passportId;
    }

    public static PassportToken from(String passportId) {
        return new PassportToken(passportId);
    }
}