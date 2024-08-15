package com.myhotel.paymentms.command.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhotel.paymentms.command.CreatePaymentCommand;
import com.myhotel.paymentms.command.UpdatePaymentCommand;
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
                .paymentId(UUID.randomUUID().toString())
                .email(paymentRequest.getEmail())
                .price(paymentRequest.getPrice())
                .roomId(paymentRequest.getRoomId())
                .paymentMethod("CREDITCARD")
                .paymentStatus(PaymentStatus.COMPLETED.name())
                .paymentDate(new Date())
                .paymentTransactionId(UUID.randomUUID().toString())
                .build();

        try {
            Message message = new Message();
            message.setEmail(paymentRequest.getEmail());
            message.setMessage("Payment completed successfully");
            message.addData("paymentId", paymentRequest.getPaymentId());
            message.addData("price", paymentRequest.getPrice());
            producerService.sendMessage("PaymentNotification", objectMapper.writeValueAsString(message));
        } catch(Exception e) {
            logger.error("Error while sending message to Kafka", e);
        }
        String paymentId = commandGateway.sendAndWait(createPaymentCommand);
        paymentId = paymentId != null && paymentId.contains("::")
                ? paymentId.split("::")[1]
                : paymentId;
        return paymentId;
    }

    public String updatePayment(PaymentRequest paymentRequest) {
        logger.info("Refunding payment for: " + paymentRequest);
        //Call to payment gateway

        UpdatePaymentCommand updatePaymentCommand = UpdatePaymentCommand.builder()
                .paymentId(paymentRequest.getPaymentId())
                .paymentStatus(paymentRequest.getPaymentStatus())
                .build();


        try {
            Message message = new Message();
            message.setEmail(paymentRequest.getEmail());
            message.setMessage("Payment refunded successfully");
            message.addData("paymentTransactionId", paymentRequest.getPaymentTransactionId());
            message.addData("price", paymentRequest.getPrice());
            producerService.sendMessage("PaymentNotification", objectMapper.writeValueAsString(message));
        } catch(Exception e) {
            logger.error("Error while sending message to Kafka", e);
        }

        String paymentId = commandGateway.sendAndWait(updatePaymentCommand);
        paymentId = paymentId != null && paymentId.contains("::")
                ? paymentId.split("::")[1]
                : paymentId;
        return paymentId;
    }

}
