package com.dalliza.eventservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dalliza.eventservice.entity.Payment;
import com.dalliza.eventservice.entity.PaymentHistory;
import com.dalliza.eventservice.entity.User;
import com.dalliza.eventservice.repository.PaymentHistoryRepository;
import com.dalliza.eventservice.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;
	private final PaymentHistoryRepository paymentHistoryRepository;

	@Transactional
	public Payment read(final Long id) {
		return paymentRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("제품을 찾을 수 없습니다"));
	}

	@Transactional
	public void pay(final User user, final Payment payment) {

		payment.decreaseAmount();

		PaymentHistory history = PaymentHistory.builder()
			.user(user)
			.payment(payment)
			.build();

		paymentHistoryRepository.save(history);
	}

	@Transactional
	public void save() {
		Payment payment = Payment.builder()
			.name("상품 A")
			.price(10000)
			.amount(20)
			.build();

		paymentRepository.save(payment);
	}
}
