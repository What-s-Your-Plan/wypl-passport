package com.cdd.sangchupassport.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class PassportAdvice {
    @ExceptionHandler(PassportException.class)
    public ResponseEntity<String> handleException(PassportException e) {
        log.warn("""

                        Passport Exception Message  : {}
                        Passport Id                 : [{}]
                        Request Member Id           : [{}]
                        """,
                e.getMessage(),
                e.getPassportId(),
                e.getMemberId());
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }
}
