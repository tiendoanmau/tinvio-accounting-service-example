package com.tinvio.accounting.controller;

import com.tinvio.accounting.model.enums.HttpResponseStatus;
import com.tinvio.accounting.model.enums.ResultCode;
import com.tinvio.accounting.model.request.account.CreateBusinessAccountRequest;
import com.tinvio.accounting.model.response.BaseResponse;
import com.tinvio.accounting.model.response.account.CreateBusinessAccountResponse;
import com.tinvio.accounting.service.AccountsService;
import com.tinvio.collect.auth.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/finance-accounting/v1")
@CrossOrigin
@RequiredArgsConstructor
public class AccountingController {

    private final AccountsService accountsService;

    @PostMapping("/create")
    @Authorization(authNOnly = true)
    public ResponseEntity<?> createFinanceBusinessAccount(@RequestBody CreateBusinessAccountRequest request) {
        log.info("Creating a business account");
        CreateBusinessAccountResponse businessAccountResponse = accountsService.createBusinessAccount(request);
        log.debug("Response from business account creation {}", businessAccountResponse.toString());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.builder()
                        .code(ResultCode.PE_S_SUCCESS.name())
                        .message(ResultCode.PE_S_SUCCESS.getDescription())
                        .data(businessAccountResponse)
                        .status(HttpResponseStatus.CREATED.toString())
                        .build());
    }
}
