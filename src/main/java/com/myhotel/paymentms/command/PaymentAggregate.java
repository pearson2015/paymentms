package com.myhotel.paymentms.command;

import com.myhotel.paymentms.core.events.PaymentCreatedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Aggregate
@NoArgsConstructor
public class PaymentAggregate {

    @AggregateIdentifier
    private String paymentId;
    private String email;
    private double price;
    private String roomId;
    private String paymentMethod;
    private String paymentStatus;
    private Date paymentDate;
    private String paymentTransactionId;

    @CommandHandler
    public PaymentAggregate(CreatePaymentCommand createPaymentCommand) {
        // Validate Update RoomRequest Command

        PaymentCreatedEvent paymentCreatedEvent = new PaymentCreatedEvent();
        BeanUtils.copyProperties(createPaymentCommand, paymentCreatedEvent);

        AggregateLifecycle.apply(paymentCreatedEvent);
    }

    @EventSourcingHandler
    public void on(PaymentCreatedEvent paymentCreatedEvent) {
        this.paymentId = paymentCreatedEvent.getPaymentId();
        this.email = paymentCreatedEvent.getEmail();
        this.price = paymentCreatedEvent.getPrice();
        this.roomId = paymentCreatedEvent.getRoomId();
        this.paymentMethod = paymentCreatedEvent.getPaymentMethod();
        this.paymentStatus = paymentCreatedEvent.getPaymentStatus();
        this.paymentDate = paymentCreatedEvent.getPaymentDate();
        this.paymentTransactionId = paymentCreatedEvent.getPaymentTransactionId();
    }


}
