package com.tinvio.accounting.model.enums;

import java.util.stream.Stream;

public enum UserType {
    SUPPLIER ("SUPPLIER"),
    MERCHANT ("MERCHANT");

    private String code;

    UserType(String code) {
        this.code = code;
    }

    public static UserType from(final String code) {
        return Stream.of(UserType.values())
                .filter(targetEnum -> targetEnum.code.equals(code))
                .findFirst()
                .orElse(null);
    }

    public String forClaims() {
        return this.name().toLowerCase();
    }
}
