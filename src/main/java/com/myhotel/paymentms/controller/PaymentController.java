package com.myhotel.paymentms.controller;

import com.myhotel.paymentms.entity.Payment;
import com.myhotel.paymentms.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/paymentms")
public class PaymentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PaymentService paymentService;

    @RequestMapping("/payments")
    public List<Payment> getAllPayments() {
        logger.info("Getting all payments");
        return paymentService.getAllPayments();
    }

    @RequestMapping("/payment/{id}")
    public Payment getPaymentById(@PathVariable ("id") Long id) {
        logger.info("Getting payment with id: " + id);
        return paymentService.getPaymentById(id);
    }

    @RequestMapping("/payment/email/{email}")
    public List<Payment> getPaymentByEmail(@PathVariable ("email") String email) {
        logger.info("Getting all payments with email: " + email);
        return paymentService.getPaymentByEmail(email);
    }

    @RequestMapping("/payment/transactionid/{transactionId}")
    public Payment getPaymentByTransactionId(@PathVariable ("transactionId") String transactionId) {
        logger.info("Getting all payments with transactionId: " + transactionId);
        return paymentService.getPaymentByTransactionId(transactionId);
    }

    @PostMapping("/payment")
    public Payment doPayment(@RequestBody Payment payment) {
        logger.info("Request for doPayment " + payment);
        return paymentService.doPayment(payment);
    }

    @PutMapping("/payment")
    public Payment cancelPayment(@RequestBody Payment payment) {
        logger.info("Request for doPayment " + payment);
        return paymentService.cancelPayment(payment);
    }

}
