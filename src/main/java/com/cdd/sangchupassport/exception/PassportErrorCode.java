package com.cdd.sangchupassport.exception;

import lombok.Getter;

@Getter
public enum PassportErrorCode {
    EXPIRED_PASSPORT(400, "PASSPORT_001", "만료된 인증입니다."),
    REUSE_PASSPORT(400, "PASSPORT_002", "이미 사용된 인증입니다."),
    INVALID_DESTINATION(400, "PASSPORT_003", "잘못된 접근입니다."),
    INVALID_PATTEN(400, "PASSPORT_004", "올바르지 않은 패턴입니다.");

    private final int statusCode;
    private final String errorCode;
    private final String message;

    PassportErrorCode(int statusCode, String errorCode, String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }
}