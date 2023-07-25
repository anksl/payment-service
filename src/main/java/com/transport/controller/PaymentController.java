package com.transport.controller;

import com.transport.api.dto.PaymentDto;
import com.transport.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentDto>> getAllPayments(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<PaymentDto> list = paymentService.getPayments(pageNo, pageSize, sortBy);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<PaymentDto>> getPaymentByPrice(
            @RequestParam(defaultValue = "100.00") BigDecimal price,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<PaymentDto> list = paymentService.findByPriceGreaterThan(price, pageNo, pageSize, sortBy);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public PaymentDto getPaymentById(@PathVariable(value = "id") Long id) {
        return paymentService.getPaymentById(id);
    }

    @PostMapping
    public void savePayment(PaymentDto paymentDto) {
        paymentService.savePayment(paymentDto);
    }

    @GetMapping("/current")
    public ResponseEntity<List<PaymentDto>> getForCurrentUser(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<PaymentDto> list = paymentService.getForCurrentUser(pageNo, pageSize, sortBy);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable(value = "id") Long id, @Validated @RequestBody PaymentDto newPayment) {
        return ResponseEntity.ok(paymentService.updatePayment(id, newPayment));
    }
}
