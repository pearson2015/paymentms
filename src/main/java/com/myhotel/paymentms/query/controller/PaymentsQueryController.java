package com.myhotel.paymentms.query.controller;

import com.myhotel.paymentms.entity.Payment;
import com.myhotel.paymentms.query.service.PaymentsQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paymentms")
public class PaymentsQueryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PaymentsQueryService paymentsQueryService;

    @GetMapping("/payment/{paymentId}")
    public Payment getPayment(@PathVariable String paymentId) {
        logger.info("Received request to get payment with ID: {}", paymentId);
        return paymentsQueryService.getPayment(paymentId);
    }
}
