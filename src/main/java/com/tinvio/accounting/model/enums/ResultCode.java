package com.tinvio.accounting.model.enums;

public enum ResultCode {

    /** ErrorCode from Client */
    PE_C_ERROR_001("INVALID_PARAM", "Invalid Parameter."),
    PE_C_ERROR_002("DUPLICATE_PAYMENT", "payment duplicated."),
    PE_C_ERROR_003("AMOUNT_INVALID", "Amount invalid."),
    PE_C_ERROR_004("CURRENCY_INVALID", "Currency invalid."),
    PE_C_ERROR_005("UNAUTHORIZED", "Invalid authorization"),

    /** ErrorCode from Payment Service Internal */
    PE_S_SUCCESS("SUCCESS", "Success"),
    PE_S_FAIL("FAIL", "Fail"),
    PE_S_ERROR_001("INTERNAL_ERROR", "Server internal error"),
    PE_S_ERROR_002("PAYMENT_NOT_SUPPORTED", "Payment not supported"),
    PE_S_ERROR_003("BENEFICIARY_NOT_FOUND", "Beneficiary not found"),
    PE_S_ERROR_004("BUSINESS_ACCOUNT_NOT_FOUND", "Business Account not found"),
    PE_S_ERROR_005("CURRENCY_MISMATCH", "Currency mismatch."),
    PE_S_ERROR_006("PAYMENT_NOT_FOUND", "Payment not found."),
    PE_S_ERROR_007("RESOURCE_NOT_FOUND", "Resource not found."),
    PE_S_ERROR_008("RAPYD_INVALID_BENEFICIARY_ACCOUNT_REFERENCE", "Invalid Beneficiary account reference for RAPYD"),
    PE_S_ERROR_009("PAYMENT_CANCELLATION_NOT_SUPPORTED", "Payment cancellation not supported"),
    PE_S_ERROR_010("INSUFFICIENT_BALANCE", "The balance is insufficient in the business account"),
    PE_S_ERROR_011("COLLECTION_DISABLED_FOR_BA", "Collection is disable for business account"),
    PE_S_ERROR_012("WITHDRAWAL_DISABLED_FOR_BA", "Withdrawal is disable for business account"),
    PE_S_ERROR_013("AUTO_WITHDRAWAL_DISABLED_FOR_BA", "Withdrawal is disable for business account"),
    PE_S_ERROR_014("INSUFFICIENT_TMA_BALANCE", "The balance is insufficient in the TMA account"),

    /** ErrorCode from TP3 */
    PE_TP_SUCCESS ("SUCCESS", "Success."),
    PE_TP_FAIL ("FAIL", "Fail."),
    PE_TP_RETURNED ("RETURNED", "Returned."),
    PE_TP_UNKNOWN ("UNKNOWN", "Unknown."),
    PE_TP_ERROR_001 ("THIRD_PARTY_ERROR", "Third party exception"),
    PE_TP_ERROR_002 ("UNMATCHED_INFO", "Unmatched Information"),
    ;

    private String status;
    private String description;

    ResultCode(String status, String description) {
        this.status = status;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}