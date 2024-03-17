package com.cdd.sangchupassport.support;

import lombok.Getter;

@Getter
public enum SangchuHeader {
    SANGCHU_PASSPORT("Sangchu-Passport");

    private final String name;

    SangchuHeader(String name) {
        this.name = name;
    }
}
