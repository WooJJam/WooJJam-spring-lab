package com.dalliza.eventservice.service;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dalliza.eventservice.entity.Notification;
import com.dalliza.eventservice.entity.Payment;
import com.dalliza.eventservice.entity.User;
import com.dalliza.eventservice.repository.NotificationRepository;
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

	private final NotificationRepository notificationRepository;

	@Value("${slack.token}")
	private String token;

	public void send(final User user, final Payment payment) {

		Random random = new Random();

		try {

			MethodsClient methods = Slack.getInstance().methods(token);

			ChatPostMessageRequest request = ChatPostMessageRequest.builder()
				.channel("결제내역")
				.text("결제 완료 알림입니다.")
				.blocks(
					asBlocks(
						header(header -> header.text(plainText("💳 " + user.getName() + " 님이 결제를 완료하였습니다."))),
						divider(),
						section(section -> section.text(markdownText(
							"*전화번호: * " + user.getPhone()
								+ "\n*결제 상품: * " + payment.getName()
								+ "\n*결제 금액: * " + new DecimalFormat("#,###").format(payment.getPrice())
								.replace(",", ".") + "원")
						))))
				.build();

			// methods.chatPostMessage(request);

			Thread.sleep(random.nextInt(200, 400));


			log.info("알림을 전송하였습니다.");
		}
		catch (Exception e) {
			log.error("error : {}", e.getMessage(), e);
		}
		// catch (IOException | SlackApiException e) {
		// 	log.error("error: {}", e.getMessage(), e);
		// }
	}
}
