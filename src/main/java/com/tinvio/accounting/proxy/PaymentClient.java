package com.tinvio.accounting.proxy;

import com.tinvio.accounting.model.request.payment.CreatePaymentRequest;
import com.tinvio.accounting.model.response.payment.CreatePaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "payment-client", url = "${paymentservice.create-payment.url}")
public interface PaymentClient {

    @RequestMapping(method = RequestMethod.POST, value = "/create-payment")
    CreatePaymentResponse createPayment(@RequestBody CreatePaymentRequest request);
}
