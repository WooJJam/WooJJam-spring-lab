package co.kr.woojjam.kakao.bizmessage.request;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AlimTalkSendReq {

	private String senderKey;
	private String templateCode;
	private List<Recipients> recipientList;

	@Getter
	@Builder
	@ToString
	public static class Recipients {
		private String recipientNo;
		private Map<String, ?> templateParameter;

	}
}
