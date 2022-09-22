package com.tinvio.accounting.model.response.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tinvio.accounting.model.dto.BusinessAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBusinessAccountResponse {
        @JsonProperty("created_account")
        private BusinessAccount createdAccount;
}
