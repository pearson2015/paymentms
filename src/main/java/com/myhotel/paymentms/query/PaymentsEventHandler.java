package com.myhotel.paymentms.query;

import com.myhotel.paymentms.core.events.PaymentCreatedEvent;
import com.myhotel.paymentms.core.events.PaymentUpdatedEvent;
import com.myhotel.paymentms.entity.Payment;
import com.myhotel.paymentms.repository.PaymentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaymentsEventHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PaymentRepository paymentRepository;

    @EventHandler
    public void on(PaymentCreatedEvent paymentCreatedEvent) {
        logger.info("Received PaymentCreatedEvent: " + paymentCreatedEvent);
        Payment payment = new Payment();
        BeanUtils.copyProperties(paymentCreatedEvent, payment);
        payment.setPaymentId(paymentCreatedEvent.getPaymentId() != null && paymentCreatedEvent.getPaymentId().contains("::")
                ? paymentCreatedEvent.getPaymentId().split("::")[1]
                : paymentCreatedEvent.getPaymentId());
        paymentRepository.save(payment);
    }

    @EventHandler
    public void on(PaymentUpdatedEvent paymentUpdatedEvent) {
        logger.info("Received PaymentUpdatedEvent: " + paymentUpdatedEvent);
        String paymentId = paymentUpdatedEvent.getPaymentId() != null && paymentUpdatedEvent.getPaymentId().contains("::")
                ? paymentUpdatedEvent.getPaymentId().split("::")[1]
                : paymentUpdatedEvent.getPaymentId();
        logger.info("PaymentId: " + paymentId);
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if(optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            payment.setPaymentStatus(paymentUpdatedEvent.getPaymentStatus());
            logger.info("Payment: " + payment);
            paymentRepository.save(payment);
        }
    }
}
