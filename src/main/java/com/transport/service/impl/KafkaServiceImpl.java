package com.transport.service.impl;

import com.transport.api.dto.DebtorsMessageDto;
import com.transport.service.KafkaService;
import com.transport.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaServiceImpl implements KafkaService {
    private final KafkaTemplate<Long, DebtorsMessageDto> kafkaStarshipTemplate;
    private final PaymentService paymentService;

    @Override
    public void findDebtors() {
        kafkaStarshipTemplate.send("server.payment", paymentService.findDebtors());
    }
}
