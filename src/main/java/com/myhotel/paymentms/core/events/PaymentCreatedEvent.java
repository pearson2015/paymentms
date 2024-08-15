package com.myhotel.paymentms.core.events;

import lombok.Data;
import java.util.Date;

@Data
public class PaymentCreatedEvent {

    private String paymentId;
    private String email;
    private double price;
    private String roomId;
    private String paymentMethod;
    private String paymentStatus;
    private Date paymentDate;
    private String paymentTransactionId;
}
