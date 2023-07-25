package com.transport.api.mapper;

import com.transport.api.dto.PaymentDto;
import com.transport.model.Payment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment convert(PaymentDto paymentDto);

    PaymentDto convert(Payment payment);

    List<PaymentDto> convert(List<Payment> payments);
}
