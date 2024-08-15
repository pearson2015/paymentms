package com.myhotel.paymentms.query.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhotel.paymentms.entity.Payment;
import com.myhotel.paymentms.query.FindPaymentByPaymentIdQuery;
import com.myhotel.paymentms.service.ProducerService;
import jakarta.transaction.Transactional;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PaymentsQueryService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private ProducerService producerService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private QueryGateway queryGateway;

    public Payment getPayment(String paymentId) {
        logger.info("Received request to get payment with ID: {}", paymentId);
        FindPaymentByPaymentIdQuery findRoomsQuery = FindPaymentByPaymentIdQuery.builder()
                .paymentId(paymentId)
                .build();
        return queryGateway.query(findRoomsQuery, Payment.class)
                .join();
    }

}
