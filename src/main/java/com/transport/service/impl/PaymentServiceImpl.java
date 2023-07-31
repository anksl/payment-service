package com.transport.service.impl;

import com.transport.api.dto.DebtorsMessageDto;
import com.transport.api.dto.PaymentDto;
import com.transport.api.dto.UserDto;
import com.transport.api.exception.NoSuchEntityException;
import com.transport.api.mapper.PaymentMapper;
import com.transport.api.mapper.UserMapper;
import com.transport.config.feign.UserServiceClient;
import com.transport.model.Payment;
import com.transport.model.User;
import com.transport.model.enums.PaymentStatus;
import com.transport.repository.PaymentRepository;
import com.transport.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.transport.model.enums.PaymentStatus.OWE;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final UserMapper userMapper;
    private final UserServiceClient userServiceClient;


    @Override
    public List<PaymentDto> getPayments(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Payment> pagedResult = paymentRepository.findAll(paging);
        return pagedResult.hasContent() ? paymentMapper.convert(pagedResult.getContent()) : new ArrayList<>();
    }

    @Override
    public List<PaymentDto> getForCurrentUser(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        UserDto user = userServiceClient.getCurrentUser();
        Page<Payment> pagedResult = paymentRepository.findByUser(userMapper.convert(user), paging);
        return pagedResult.hasContent() ? paymentMapper.convert(pagedResult.getContent()) : new ArrayList<>();
    }

    @Override
    public List<PaymentDto> findByPriceGreaterThan(BigDecimal price, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Payment> pagedResult = paymentRepository.findByPriceGreaterThan(price, paging);
        return pagedResult.hasContent() ? paymentMapper.convert(pagedResult.getContent()) : new ArrayList<>();
    }

    @Override
    public PaymentDto getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format("Payment with id: %s doesn't exist", id)));
        return paymentMapper.convert(payment);
    }

    @Override
    public void savePayment(PaymentDto paymentDto) {
        Payment payment = paymentMapper.convert(paymentDto);
        setPaymentStatus(payment);
        paymentRepository.save(payment);
    }

    @Transactional
    @Override
    public PaymentDto updatePayment(Long id, PaymentDto newPaymentDto) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format("Payment with id: %s doesn't exist", id)));
        Payment newPayment = paymentMapper.convert(newPaymentDto);
        payment.setPrice(newPayment.getPrice());
        payment.setDate(newPayment.getDate());
        payment.setDeadline(newPayment.getDeadline());
        setPaymentStatus(payment);
        payment.setUser(newPayment.getUser());
        return paymentMapper.convert(paymentRepository.save(payment));
    }

    @Override
    public DebtorsMessageDto findDebtors() {
        String userEmail = userServiceClient.getCurrentUser().getEmail();
        List<String> recipients = paymentRepository.findByPaymentStatusOWE().stream().map(User::getEmail).collect(Collectors.toList());
        return new DebtorsMessageDto(userEmail, recipients);
    }

    private void setPaymentStatus(Payment payment) {
        if (payment.getDate() == null) {
            payment.setPaymentStatus(OWE);
        } else if (payment.getDeadline().after(payment.getDate())) {
            payment.setPaymentStatus(PaymentStatus.ON_TIME);
        } else {
            payment.setPaymentStatus(PaymentStatus.LATE);
        }
    }
}
