package com.myhotel.paymentms.service;

import com.myhotel.paymentms.entity.Payment;
import com.myhotel.paymentms.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PaymentServiceTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @MockBean
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    private static Payment  payment;

    @BeforeAll
    public static void setUp() {
        payment = new Payment(1L,
                "test@test.com",
                100,
                "CREDITCARD",
                "SUCCESS",
                new Date(),
                UUID.randomUUID().toString());
    }

    @Test
    @DisplayName("Test to get all payments")
    public void testGetAllPayments() {
        logger.info("Getting all payments from service layer");
        when(paymentRepository.findAll()).thenReturn(List.of(payment));
        List<Payment> payments= paymentService.getAllPayments();
        assert payments.size() == 1;
        assert payments.getFirst().getPrice() == 100;
    }

    @Test
    @DisplayName("Test to get all payments with no payments")
    public void testGetAllPaymentsWithNoPayments() {
        logger.info("Getting all payments from service layer");
        when(paymentRepository.findAll()).thenReturn(List.of());
        List<Payment> payments= paymentService.getAllPayments();
        assert payments.isEmpty();
    }

    @Test
    @DisplayName("Test to get all payments with null payments")
    public void testGetAllPaymentsWithNullPayments() {
        logger.info("Getting all payments from service layer");
        when(paymentRepository.findAll()).thenReturn(null);
        List<Payment> payments= paymentService.getAllPayments();
        assert payments == null;
    }

    @Test
    @DisplayName("Test to get all payments with multiple payments")
    public void testGetAllPaymentsWithMultiplePayments() {
        logger.info("Getting all payments from service layer");
        Payment payment1 = new Payment(1L,
                "test1@test.com",
                100,
                "CREDITCARD",
                "SUCCESS",
                new Date(),
                UUID.randomUUID().toString());
        Payment payment2 = new Payment(1L,
                "test2@test.com",
                200,
                "CREDITCARD",
                "SUCCESS",
                new Date(),
                UUID.randomUUID().toString());
        when(paymentRepository.findAll()).thenReturn(List.of(payment1, payment2));
        List<Payment> payments= paymentService.getAllPayments();
        assert payments.size() == 2;
    }

    @Test
    @DisplayName("Test to get payment by id")
    public void testGetPaymentById() {
        logger.info("Getting payment by id from service layer");
        when(paymentRepository.findById(1L)).thenReturn(ofNullable(payment));
        Payment payment= paymentService.getPaymentById(1L);
        assert payment != null;
        assert payment.getPrice() == 100;
    }

    @Test
    @DisplayName("Test to get payment by id with no payment")
    public void testGetPaymentByIdWithNoPayment() {
        logger.info("Getting payment by id from service layer");
        when(paymentRepository.findById(1L)).thenReturn(empty());
        Payment payment= paymentService.getPaymentById(1L);
        assert payment == null;
    }

}
