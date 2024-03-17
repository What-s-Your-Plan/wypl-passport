package com.cdd.sangchupassport.support;

import com.cdd.sangchupassport.Passport;
import com.cdd.sangchupassport.exception.PassportErrorCode;
import com.cdd.sangchupassport.exception.PassportException;
import com.cdd.sangchupassport.token.PassportTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PassportValidator {
    private final PassportTokenRepository repository;
    private final String destination;

    @Autowired
    public PassportValidator(
            PassportTokenRepository repository,
            @Value("${spring.application.name}") String destination
    ) {
        this.repository = repository;
        this.destination = destination;
    }

    public void validatePassword(final Passport passport) {
        validateExpiredTime(passport);
        validateDestination(passport);
        validateReuse(passport);
        passport.stamp(repository);
    }

    private void validateExpiredTime(final Passport passport) {
        long now = System.currentTimeMillis();
        if (now > passport.getExpiredTime()) {
            throw new PassportException(passport, PassportErrorCode.EXPIRED_PASSPORT);
        }
    }

    private void validateDestination(final Passport passport) {
        if (passport.isEqualToDestination(destination)) {
            throw new PassportException(passport, PassportErrorCode.INVALID_DESTINATION);
        }
    }

    private void validateReuse(final Passport passport) {
        if (repository.existsById(passport.getId())) {
            throw new PassportException(passport, PassportErrorCode.REUSE_PASSPORT);
        }
    }
}
