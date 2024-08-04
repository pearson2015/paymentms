package com.myhotel.paymentms.repository;

import com.myhotel.paymentms.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

    List<Payment> findByEmail(String email);

    Payment findByPaymentTransactionId(String paymentTransactionId);

    void deleteByEmail(String email);
}
