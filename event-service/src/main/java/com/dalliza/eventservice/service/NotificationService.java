package com.dalliza.eventservice.service;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;

import java.io.IOException;
import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dalliza.eventservice.entity.Payment;
import com.dalliza.eventservice.entity.User;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

	@Value("${slack.token}")
	private String token;

	public void send(final User user, final Payment payment) {
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
