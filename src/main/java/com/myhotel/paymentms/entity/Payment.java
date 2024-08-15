package com.myhotel.paymentms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "payment")
public class Payment {

    @Id
    private String paymentId;
    private String email;
    private double price;
    private String roomId;
    private String paymentMethod;
    private String paymentStatus;
    private Date paymentDate;
    private String paymentTransactionId;

}
