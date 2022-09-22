package com.tinvio.accounting.model.enums;

import java.util.stream.Stream;

public enum AccountStatus {
    INACTIVE ("INACTIVE"),
    ACTIVE ("ACTIVE"),
    CREATED ("CREATED");

    private String code;

    AccountStatus(String code) {
        this.code = code;
    }

    public static AccountStatus from(final String code) {
        return Stream.of(AccountStatus.values())
                .filter(targetEnum -> targetEnum.code.equals(code))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return this.name().toUpperCase();
    }
}
