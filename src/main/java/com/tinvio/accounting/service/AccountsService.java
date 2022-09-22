package com.tinvio.accounting.service;

import com.tinvio.accounting.model.request.account.CreateBusinessAccountRequest;
import com.tinvio.accounting.model.response.account.CreateBusinessAccountResponse;


public interface AccountsService {
    /**
     * Create business account
     * @param request business account request
     * @return CreateBusinessAccountResponse
     */
    CreateBusinessAccountResponse createBusinessAccount(CreateBusinessAccountRequest request);
}
