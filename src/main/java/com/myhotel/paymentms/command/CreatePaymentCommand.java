package com.myhotel.paymentms.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import java.util.Date;

@Data
@Builder
public class CreatePaymentCommand {
    @TargetAggregateIdentifier
    private final String paymentId;
    private final String email;
    private final double price;
    private final String roomId;
    private final String paymentMethod;
    private final String paymentStatus;
    private final Date paymentDate;
    private final String paymentTransactionId;
}
