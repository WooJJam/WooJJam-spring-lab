package com.dalliza.eventservice.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.dalliza.eventservice.entity.Payment;
import com.dalliza.eventservice.entity.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentCompletedEventPublisher {

	private final ApplicationEventPublisher eventPublisher;

	public void publishCompleteEvent(final Payment payment, final User user) {
		PaymentCompletedEvent event = new PaymentCompletedEvent(payment, user);
		eventPublisher.publishEvent(event);
	}
}
