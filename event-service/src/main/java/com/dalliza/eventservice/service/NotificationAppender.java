package com.dalliza.eventservice.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dalliza.eventservice.entity.Notification;
import com.dalliza.eventservice.entity.User;
import com.dalliza.eventservice.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationAppender {

	private final NotificationRepository notificationRepository;

	@Transactional
	public void append(final User user) {

		Notification notification = Notification.builder()
			.name("결제 알림 내역")
			.user(user)
			.build();

		notificationRepository.save(notification);

		log.info("알림 내역을 저장하였습니다.");
	}
}
