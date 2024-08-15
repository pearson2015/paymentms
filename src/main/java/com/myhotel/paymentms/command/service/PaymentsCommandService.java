package com.myhotel.paymentms.command.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhotel.paymentms.command.CreatePaymentCommand;
import com.myhotel.paymentms.dto.PaymentRequest;
import com.myhotel.paymentms.entity.Message;
import com.myhotel.paymentms.service.ProducerService;
import jakarta.transaction.Transactional;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class PaymentsCommandService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private ProducerService producerService;

    ObjectMapper objectMapper = new ObjectMapper();

    private enum PaymentStatus {COMPLETED, FAILED}

    public String createPayment(PaymentRequest paymentRequest) {
        logger.info("Processing payment for: " + paymentRequest);
        //Call to payment gateway

        CreatePaymentCommand createPaymentCommand = CreatePaymentCommand.builder()
                .paymentId(paymentRequest.getPaymentId() != null
                        ? paymentRequest.getPaymentId()
                        : UUID.randomUUID().toString())
                .email(paymentRequest.getEmail())
                .price(paymentRequest.getPrice())
                .roomId(paymentRequest.getRoomId())
                .paymentMethod("CREDITCARD")
                .paymentStatus(paymentRequest.getPaymentStatus() != null
                        ? paymentRequest.getPaymentStatus()
                        : PaymentStatus.COMPLETED.name())
                .paymentDate(new Date())
                .paymentTransactionId(UUID.randomUUID().toString())
                .build();


        try {
            Message message = new Message();
            message.setEmail(paymentRequest.getEmail());
            message.setMessage("Payment "
                    + createPaymentCommand.getPaymentStatus().toLowerCase()
                    + " successfully");
            message.addData("paymentTransactionId", paymentRequest.getPaymentTransactionId());
            message.addData("price", paymentRequest.getPrice());
            producerService.sendMessage("PaymentNotification", objectMapper.writeValueAsString(message));
        } catch(Exception e) {
            logger.error("Error while sending message to Kafka", e);
        }

        return commandGateway.sendAndWait(createPaymentCommand);
    }

}
