package com.myhotel.paymentms.dto;

import lombok.Data;
import lombok.ToString;
import java.util.Date;

@Data
@ToString
public class PaymentRequest {

    private String paymentId;
    private String email;
    private double price;
    private String roomId;
    private String paymentMethod;
    private String paymentStatus;
    private Date paymentDate;
    private String paymentTransactionId;
}
