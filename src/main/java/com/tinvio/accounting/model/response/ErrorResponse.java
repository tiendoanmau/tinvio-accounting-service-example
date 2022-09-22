package com.tinvio.accounting.model.response;

import com.tinvio.accounting.model.enums.HttpResponseStatus;
import com.tinvio.accounting.model.enums.ResultCode;

public class ErrorResponse extends BaseResponse<ErrorResponseBody> {

    public ErrorResponse(String message, ResultCode code) {
        super(HttpResponseStatus.FAILED.toString(), message, code.toString());
    }

    public ErrorResponse(String code, String message, String status) {
        super(status, message, code);
    }
}
