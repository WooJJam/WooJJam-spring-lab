package com.dalliza.eventservice.service;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;

import java.io.IOException;
import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dalliza.eventservice.entity.Payment;
import com.dalliza.eventservice.entity.PaymentHistory;
import com.dalliza.eventservice.entity.User;
import com.dalliza.eventservice.repository.PaymentHistoryRepository;
import com.dalliza.eventservice.repository.PaymentRepository;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;
	private final PaymentHistoryRepository paymentHistoryRepository;

	@Value("${slack.token}")
	private String token;

	@Transactional
	public Payment read(final Long id) {
		return paymentRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("제품을 찾을 수 없습니다"));
	}

	@Transactional
	public void pay(final User user, final Payment payment) throws InterruptedException {

		payment.decreaseAmount();

		PaymentHistory history = PaymentHistory.builder()
			.user(user)
			.payment(payment)
			.build();

		paymentHistoryRepository.save(history);

		log.info("history Id = {}", history.getId());
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

	private void send(final User user, final Payment payment) {
		try {
			MethodsClient methods = Slack.getInstance().methods(token);

			ChatPostMessageRequest request = ChatPostMessageRequest.builder()
				.channel("결제내역")
				.blocks(
					asBlocks(
						header(header -> header.text(plainText("💳 " + user.getName() + " 님이 결제를 완료하였습니다."))),
						divider(),
						section(section -> section.text(markdownText(
							"*전화번호: * " + user.getPhone()
								+ "\n*결제 상품: * " + payment.getName()
								+ "\n*결제 금액: * " + new DecimalFormat("#,###").format(payment.getPrice()).replace(",", ".") + "원")
						))))
				.build();

			methods.chatPostMessage(request);

			log.info("request {}", request);
		} catch (IOException | SlackApiException e) {
			log.error("error: {}", e.getMessage(), e);
		}
	}
}
