package com.myhotel.paymentms.service;

import com.myhotel.paymentms.entity.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhotel.paymentms.entity.Payment;
import com.myhotel.paymentms.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PaymentRepository paymentRepository;

    ObjectMapper  objectMapper = new ObjectMapper();

    public List<Payment> getAllPayments() {
        logger.info("Getting all payments from service layer");
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(long id) {
        logger.info("Getting payment with id: " + id + " from service layer");
        return paymentRepository.findById(id).orElse(null);
    }

    public List<Payment> getPaymentByEmail(String email) {
        logger.info("Getting all payments with email: " + email + " from service layer");
        return paymentRepository.findByEmail(email);
    }

    public Payment doPayment(Payment payment) {
        logger.info("Initiating payment: " + payment + " from service layer");
        //Call to payment gateway
        payment.setPaymentTransactionId(UUID.randomUUID().toString());
        payment.setPaymentStatus("SUCCESS");
        payment.setPaymentDate(new Date());
        payment.setPaymentMethod("CREDITCARD");
        logger.info("Paid payment: " + payment);

        try {
            Message message = new Message();
            message.setEmail(payment.getEmail());
            message.setMessage("Payment completed successfully");
            message.addData("paymentTransactionId", payment.getPaymentTransactionId());
            message.addData("amount", payment.getAmount());

        } catch(Exception e) {
            logger.error("Error while sending message to SQS", e);
        }

        return paymentRepository.save(payment);
    }

    public boolean refundPayment(long id) {
        Payment payment = paymentRepository.findById(id).orElse(null);
        if(payment == null)
            return false;
        logger.info("Refunding payment with Id: " + id + " from service layer");
        try {
            Message message = new Message();
            message.setEmail(payment.getEmail());
            message.setMessage("Payment refunded successfully");
            message.addData("paymentTransactionId", payment.getPaymentTransactionId());
            message.addData("amount", payment.getAmount());

            //sqsService.sendMessage(objectMapper.writeValueAsString(message));
        } catch(Exception e) {
            logger.error("Error while sending message to SQS", e);
        }

        paymentRepository.deleteById(id);
        return true;
    }

    public void refundAllPaymentByEmail(String email) {
        logger.info("Refunding all payments with email: " + email + " from service layer");
        paymentRepository.deleteByEmail(email);
    }

}
