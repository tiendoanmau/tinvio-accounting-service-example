package com.tinvio.accounting.model.enums;

public enum ErrorCode {
    TI_AS_ERROR_001,
    TI_AS_ERROR_002,
    TI_AS_ERROR_003,
    TI_AS_ERROR_004,
    TI_AS_ERROR_005,
    TI_AS_ERROR_006,
    TI_AS_ERROR_007,
    TI_AS_ERROR_008,
    TI_AS_ERROR_009,
    TI_AS_ERROR_010;

    ErrorCode() {
    }

    @Override
    public String toString() {
         return this.name().toUpperCase();
    }
}
