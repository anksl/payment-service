package com.transport.controller;

import com.transport.api.dto.DebtorsMessageDto;
import com.transport.service.KafkaService;
import com.transport.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messages")
public class KafkaController {

    private final KafkaService kafkaService;

    @Scheduled(cron = "${com.transport.cron.customer-reminder}")
    @GetMapping("/debtors")
    public void findDebtors() {
        kafkaService.findDebtors();
    }
}