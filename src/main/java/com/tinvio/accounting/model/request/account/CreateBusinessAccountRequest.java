package com.tinvio.accounting.model.request.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tinvio.accounting.model.dto.Flags;
import com.tinvio.accounting.model.request.BaseRequest;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBusinessAccountRequest extends BaseRequest {

    @JsonProperty("business_account_name")
    private String businessAccountName;

    @JsonProperty("flags")
    private Flags flags;

    @JsonProperty("fees_id")
    private Integer feesId;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("account_type")
    private String accountType;

}
