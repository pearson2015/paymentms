package com.myhotel.paymentms.command.controller;

import com.myhotel.paymentms.command.service.PaymentsCommandService;
import com.myhotel.paymentms.dto.PaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paymentms")
public class PaymentsCommandController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PaymentsCommandService paymentsCommandService;

    @PostMapping("/payment")
    public String doPayment(@RequestBody PaymentRequest payment) {
        logger.info("Request for doPayment " + payment);
        return paymentsCommandService.createPayment(payment);
    }

    @PutMapping("/payment")
    public String updatePayment(@RequestBody PaymentRequest payment) {
        logger.info("Request for updatePayment " + payment);
        return paymentsCommandService.updatePayment(payment);
    }
}
