package com.dalliza.eventservice.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.dalliza.eventservice.entity.Notification;
import com.dalliza.eventservice.repository.NotificationRepository;
import com.dalliza.eventservice.service.NotificationAppender;
import com.dalliza.eventservice.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentCompletedEventListener {

	private final NotificationAppender notificationAppender;
	private final NotificationService notificationService;

	@TransactionalEventListener
	public void sendCompletedMessage(final PaymentCompletedEvent event) throws InterruptedException {

		log.info("PaymentCompletedEvent 를 수신하였습니다.");

		notificationAppender.save(event.user());
		notificationService.send(event.user(), event.payment());
	}
}
