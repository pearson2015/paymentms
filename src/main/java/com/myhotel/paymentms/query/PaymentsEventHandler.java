package com.myhotel.paymentms.query;

import com.myhotel.paymentms.core.events.PaymentCreatedEvent;
import com.myhotel.paymentms.entity.Payment;
import com.myhotel.paymentms.repository.PaymentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        paymentRepository.save(payment);
    }
}
