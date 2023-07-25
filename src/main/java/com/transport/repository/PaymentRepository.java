package com.transport.repository;

import com.transport.model.Payment;
import com.transport.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Page<Payment> findByPriceGreaterThan(BigDecimal price, Pageable pageable);

    Page<Payment> findByUser(User user, Pageable pageable);

    @Query("select p.user from Payment p where p.paymentStatus = com.transport.model.enums.PaymentStatus.OWE")
    List<User> findByPaymentStatusOWE();
}
