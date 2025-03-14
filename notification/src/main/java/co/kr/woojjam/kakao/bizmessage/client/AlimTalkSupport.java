package co.kr.woojjam.kakao.bizmessage.client;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import co.kr.woojjam.kakao.bizmessage.request.AlimTalkSendReq;
import co.kr.woojjam.labsdomain.domains.User;

@Component
public class AlimTalkSupport {

    private final String appKey;

	private final String sendKey;

	public AlimTalkSupport(
		@Value("${KAKAO_BIZ_MESSAGE_APP_KEY}") final String appKey,
		@Value("${KAKAO_BIA_MESSAGE_SEND_KEY}") final String sendKey,
		final AlimTalkClient alimTalkClient) {
		this.appKey = appKey;
		this.sendKey = sendKey;
		this.alimTalkClient = alimTalkClient;
	}

	private final AlimTalkClient alimTalkClient;


	public Object sendMessage(User user) {

		Map<String, String> templateParameter = Map.of(
			"이름", "우쨈",
			"매치이름", "갤럭시 구장",
			"매치시간/날짜", "12-19 01:00",
			"매치구장", "수성동 갤럭시 1 구장",
			"id", "2"
		);

		AlimTalkSendReq.Recipients recipient1 = AlimTalkSendReq.Recipients.builder()
			.recipientNo(user.getPhoneNumber())
			.templateParameter(templateParameter)
			.build();

		AlimTalkSendReq request = AlimTalkSendReq.builder()
			.senderKey(sendKey)
			.recipientList(List.of(recipient1))
			.templateCode("sponjy001")
			.build();

		return alimTalkClient.send(appKey, request);
	}
	
	public Object readTemplate() {
		System.out.println("appKey = " + appKey);
		System.out.println("sendKey = " + sendKey);
		return alimTalkClient.readTemplate(appKey, sendKey);
	}
}
