package com.transport.api.dto;

import com.transport.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long id;
    private Date date;
    private Date deadline;
    @Positive
    private BigDecimal price;
    private PaymentStatus paymentStatus;
    private SecureUserDto user;
}
