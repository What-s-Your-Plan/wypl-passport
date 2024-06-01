package com.wypl.passport.support;

import lombok.Getter;

@Getter
public enum CustomHeaders {
	WYPL_PASSPORT("Wypl-Passport");

	private final String name;

	CustomHeaders(String name) {
		this.name = name;
	}
}
