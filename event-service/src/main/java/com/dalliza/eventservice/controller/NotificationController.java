package com.dalliza.eventservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dalliza.eventservice.entity.Payment;
import com.dalliza.eventservice.entity.User;
import com.dalliza.eventservice.service.NotificationService;
import com.dalliza.eventservice.service.PaymentService;
import com.dalliza.eventservice.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/slack")
public class NotificationController {

	private final PaymentService paymentService;
	private final UserService userService;
	private final NotificationService notificationService;

	@PostMapping("/send")
	public void sendMessage() {
		Payment payment = paymentService.read(1L);
		User user = userService.read(1L);
		notificationService.send(user, payment);
	}
}
