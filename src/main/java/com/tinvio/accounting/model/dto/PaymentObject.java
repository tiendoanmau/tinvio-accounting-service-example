package com.tinvio.accounting.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentObject {


    @JsonProperty("payment_id")
    private Integer paymentId;

    @JsonProperty("payment_reference")
    private String paymentReference;

    @JsonProperty("status")
    private String status;

    @JsonProperty("payment_instrument")
    private PaymentInstrument paymentInstrument;

    @JsonProperty("fee_amount_supplier")
    private BigDecimal feeAmountSupplier;

    @JsonProperty("fee_amount_outlet")
    private BigDecimal feeAmountOutlet;

    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("account_holder")
    private String accountHolder;
    @JsonProperty("bank_name")
    private String bankName;
    @JsonProperty("bank_code")
    private String bankCode;
    @JsonProperty("description")
    private String description;
    @JsonProperty("initiator")
    private String initiator;
}
