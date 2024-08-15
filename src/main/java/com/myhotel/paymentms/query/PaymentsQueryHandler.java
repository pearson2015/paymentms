package com.myhotel.paymentms.query;

import com.myhotel.paymentms.entity.Payment;
import com.myhotel.paymentms.repository.PaymentRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentsQueryHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PaymentRepository paymentRepository;

    @QueryHandler
    public Payment findPayment(FindPaymentByPaymentIdQuery findPaymentByPaymentIdQuery) {
        logger.info("Received FindPaymentByPaymentIdQuery: " + findPaymentByPaymentIdQuery);
        return paymentRepository.findById(findPaymentByPaymentIdQuery.getPaymentId())
                .orElse(null);
    }
}
