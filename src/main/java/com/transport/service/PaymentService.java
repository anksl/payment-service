package com.transport.service;

import com.transport.api.dto.PaymentDto;
import com.transport.api.dto.DebtorsMessageDto;
import com.transport.model.Payment;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {

    List<PaymentDto> getPayments(Integer pageNo, Integer pageSize, String sortBy);

    List<PaymentDto> findByPriceGreaterThan(BigDecimal price, Integer pageNo, Integer pageSize, String sortBy);

    List<PaymentDto> getForCurrentUser(Integer pageNo, Integer pageSize, String sortBy);

    PaymentDto getPaymentById(Long id);

    void savePayment(PaymentDto payment);

    PaymentDto updatePayment(Long id, PaymentDto newPayment);

    DebtorsMessageDto findDebtors();
}
