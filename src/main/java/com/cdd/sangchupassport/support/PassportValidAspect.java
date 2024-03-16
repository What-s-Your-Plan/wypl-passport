package com.cdd.sangchupassport.support;

import com.cdd.sangchupassport.Passport;
import com.cdd.sangchupassport.exception.PassportErrorCode;
import com.cdd.sangchupassport.exception.PassportException;
import com.cdd.sangchupassport.token.PassportTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class PassportValidAspect {
    private final PassportTokenRepository repository;

    @Value("${spring.application.name}")
    private String destination;

    @Pointcut("@annotation(com.cdd.sangchupassport.support.ValidatePassport)")
    private void targetParam() {

    }

    @Before("targetParam()")
    public void checkPassport(final JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Passport passport) {
                validateExpiredTime(passport);
                validateDestination(passport);
                validateReuse(passport);
                passport.stamp(repository);
            }
        }
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
        if (repository.findById(passport.getId()).isPresent()) {
            throw new PassportException(passport, PassportErrorCode.REUSE_PASSPORT);
        }
    }
}
