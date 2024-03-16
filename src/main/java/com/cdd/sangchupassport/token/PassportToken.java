package com.cdd.sangchupassport.token;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(timeToLive = 5)
public class PassportToken {
    @Id
    public String passportId;

    public static PassportToken from(String passportId) {
        return new PassportToken(passportId);
    }
}