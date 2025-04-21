package com.dalliza.eventservice.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dalliza.eventservice.entity.Payment;
import com.dalliza.eventservice.entity.User;
import com.dalliza.eventservice.service.PaymentService;
import com.dalliza.eventservice.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentUseCase {

	private final UserService userService;
	private final PaymentService paymentService;

	@Transactional
	public void init() {
		userService.save();
		paymentService.save();
	}

	@Transactional
	public void pay(final Long paymentId, final Long userId) {
		Payment payment = paymentService.read(paymentId);
		User user = userService.read(userId);
		paymentService.pay(user, payment);
	}
}
