package com.tinvio.accounting.model.response;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Builder
@Slf4j
public class BaseResponse<T> {
    public final T data;
    public final String status;
    public final String message;
    public final String code;
    public final String requestId;

    /**
     * Creates a GatewayResponse object.
     *
     * @param data    data of the response
     * @param code    status code of the response
     * @param message detailed message of the response
     * @param status  enum status of the response ("SUCCESS", "FAILED", etc.)
     */
    public BaseResponse(final T data, final String status, final String message,
                        final String code, String requestId) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.status = status;
        this.requestId = MDC.get("correlationId");
    }

    public BaseResponse(final String status, final String message, final String code) {
        this((T) "{}", status, message, code);
    }

    public BaseResponse(final T data, String status, String message, String code) {
        this(data, status, message, code, null);
    }
}
