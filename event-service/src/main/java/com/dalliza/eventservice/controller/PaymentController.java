package com.dalliza.eventservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dalliza.eventservice.dto.PaymentRequest;
import com.dalliza.eventservice.usecase.PaymentUseCase;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

	private final PaymentUseCase paymentUseCase;

	@PostConstruct
	public void init() {
		paymentUseCase.init();
	}

	@PostMapping("/pay")
	public void pay(@RequestBody PaymentRequest request) throws InterruptedException {
		paymentUseCase.pay(request.paymentId(), request.userId());
	}
}
