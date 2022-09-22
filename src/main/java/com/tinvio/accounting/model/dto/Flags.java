package com.tinvio.accounting.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Flags {

    @JsonProperty("is_collection_enabled")
    private boolean isCollectionEnabled;
    @JsonProperty("is_withdrawal_enabled")
    private boolean isWithdrawalEnabled;
    @JsonProperty("is_autowithdrawal_enabled")
    private boolean isAutoWithdrawalEnabled;
    @JsonProperty("test")
    private boolean test;
    @JsonProperty("verified")
    private boolean verified;

}
