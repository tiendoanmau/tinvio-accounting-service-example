package com.tinvio.accounting.model.enums;

import java.util.stream.Stream;

public enum AccountType {

    TMA ("TMA"),
    TRA ("TRA"),
    BA ("BA");

    private String code;

    AccountType(String code) {
        this.code = code;
    }

    public static AccountType from(final String code) {
        return Stream.of(AccountType.values())
                .filter(targetEnum -> targetEnum.code.equals(code))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return this.name().toUpperCase();
    }
}
