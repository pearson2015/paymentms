package com.myhotel.paymentms.service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SQSService {

    @Autowired
    private AmazonSQSAsync amazonSQSAsync;

    @Value("${system.aws.sqs.name}")
    private String queueUrl;

    public void sendMessage(String message) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(message);

        amazonSQSAsync.sendMessageAsync(sendMessageRequest);
    }
}

