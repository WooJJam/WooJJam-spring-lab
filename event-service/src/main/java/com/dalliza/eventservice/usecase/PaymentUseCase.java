package com.dalliza.eventservice.usecase;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dalliza.eventservice.entity.Payment;
import com.dalliza.eventservice.entity.User;
import com.dalliza.eventservice.event.PaymentCompletedEvent;
import com.dalliza.eventservice.event.PaymentCompletedEventPublisher;
import com.dalliza.eventservice.service.NotificationService;
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
	private final NotificationService notificationService;
	private final PaymentCompletedEventPublisher eventPublisher;
	private final ApplicationEventPublisher applicationEventPublisher;

	@Transactional
	public void init() {
		userService.save();
		paymentService.save();
	}

	@Transactional
	public void pay(final Long paymentId, final Long userId) throws InterruptedException {
		Payment payment = paymentService.read(paymentId);
		User user = userService.read(userId);
		paymentService.pay(user, payment);
		log.info("결제가 완료 되었습니다. 이벤트를 발행합니다.");
		applicationEventPublisher.publishEvent(new PaymentCompletedEvent(payment, user));
		log.info("pay() 메소드 종료");
		// eventPublisher.publishCompleteEvent(payment, user);
	}
}
