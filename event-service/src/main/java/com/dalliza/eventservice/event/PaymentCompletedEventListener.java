package com.dalliza.eventservice.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.dalliza.eventservice.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentCompletedEventListener {

	private final NotificationService notificationService;

	// @TransactionalEventListener
	@EventListener
	public void sendCompletedMessage(final PaymentCompletedEvent event) throws InterruptedException {

		// throw new IllegalArgumentException("일부로 오류 발생");
		log.info("PaymentCompletedEvent 를 수신하였습니다.");
		notificationService.send(event.user(), event.payment());
	}
}
