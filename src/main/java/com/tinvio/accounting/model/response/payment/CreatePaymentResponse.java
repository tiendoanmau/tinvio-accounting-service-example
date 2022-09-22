package com.tinvio.accounting.model.response.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tinvio.accounting.model.dto.PaymentObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentResponse {

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private PaymentObject data;

    @JsonProperty("status")
    private String status;
}
