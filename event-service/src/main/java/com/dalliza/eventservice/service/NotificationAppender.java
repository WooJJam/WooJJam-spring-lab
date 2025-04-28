package com.dalliza.eventservice.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dalliza.eventservice.entity.Notification;
import com.dalliza.eventservice.entity.User;
import com.dalliza.eventservice.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationAppender {

	private final NotificationRepository notificationRepository;

	@Transactional
	public void save(final User user) {

		Notification notification = Notification.builder()
			.name("결제 알림 내역")
			.user(user)
			.build();

		notificationRepository.save(notification);
	}
}
