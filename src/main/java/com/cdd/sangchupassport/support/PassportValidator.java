package com.cdd.sangchupassport.support;

import com.cdd.sangchupassport.Passport;
import com.cdd.sangchupassport.exception.PassportErrorCode;
import com.cdd.sangchupassport.exception.PassportException;
import com.cdd.sangchupassport.token.PassportToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public class PassportValidator {
    private final CrudRepository<PassportToken, String> repository;
    private final String destination;

    @Autowired
    public PassportValidator(
            CrudRepository<PassportToken, String> repository,
            String destination
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
