package com.myhotel.paymentms.repository;

import com.myhotel.paymentms.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

    public List<Payment> findByEmail(String email);
    public void deleteByEmail(String email);
}
