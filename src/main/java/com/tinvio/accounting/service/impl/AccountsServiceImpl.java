package com.tinvio.accounting.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinvio.accounting.model.request.account.CreateBusinessAccountRequest;
import com.tinvio.accounting.model.response.account.CreateBusinessAccountResponse;
import com.tinvio.accounting.repository.BusinessAccountRepository;
import com.tinvio.accounting.service.AccountsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountsServiceImpl implements AccountsService {

    private final BusinessAccountRepository businessAccountRepository;
    private final ObjectMapper objectMapper;

    @Override
    public CreateBusinessAccountResponse createBusinessAccount(CreateBusinessAccountRequest request) {
        return null;
    }
}
