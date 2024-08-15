package com.myhotel.paymentms.command;

import com.myhotel.paymentms.core.events.PaymentCreatedEvent;
import com.myhotel.paymentms.core.events.PaymentUpdatedEvent;
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
        // Validate Create Payment Command

        PaymentCreatedEvent paymentCreatedEvent = new PaymentCreatedEvent();
        BeanUtils.copyProperties(createPaymentCommand, paymentCreatedEvent);

        AggregateLifecycle.apply(paymentCreatedEvent);
    }

    @CommandHandler
    public PaymentAggregate(UpdatePaymentCommand updatePaymentCommand) {
        // Validate Update Payment Command

        PaymentUpdatedEvent paymentUpdatedEvent = new PaymentUpdatedEvent();
        BeanUtils.copyProperties(updatePaymentCommand, paymentUpdatedEvent);

        AggregateLifecycle.apply(paymentUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(PaymentCreatedEvent paymentCreatedEvent) {
        this.paymentId = "Create::" + paymentCreatedEvent.getPaymentId();
        this.email = paymentCreatedEvent.getEmail();
        this.price = paymentCreatedEvent.getPrice();
        this.roomId = paymentCreatedEvent.getRoomId();
        this.paymentMethod = paymentCreatedEvent.getPaymentMethod();
        this.paymentStatus = paymentCreatedEvent.getPaymentStatus();
        this.paymentDate = paymentCreatedEvent.getPaymentDate();
        this.paymentTransactionId = paymentCreatedEvent.getPaymentTransactionId();
    }

    @EventSourcingHandler
    public void on(PaymentUpdatedEvent paymentUpdatedEvent) {
        this.paymentId = "Update::" + paymentUpdatedEvent.getPaymentId();
        this.paymentStatus = paymentUpdatedEvent.getPaymentStatus();
    }

}
