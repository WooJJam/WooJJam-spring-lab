package co.kr.woojjam.kakao.bizmessage.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import co.kr.woojjam.kakao.bizmessage.config.AlimTalkHeaderConfig;
import co.kr.woojjam.kakao.bizmessage.config.FeignConfig;
import co.kr.woojjam.kakao.bizmessage.request.AlimTalkSendReq;

@FeignClient(
	name = "AlimTalkClient",
	url = "https://api-alimtalk.cloud.toast.com",
	configuration = {
		FeignConfig.class,
		AlimTalkHeaderConfig.class
	}
)
public interface AlimTalkClient {

	@PostMapping("/alimtalk/v2.3/appkeys/{appkey}/messages")
	Object send(
		@PathVariable("appkey") @Value("KAKAO_BIZ_MESSAGE_APP_KEY") String appKey,
		@RequestBody AlimTalkSendReq request
	);

	@GetMapping("/alimtalk/v2.3/appkeys/{appkey}/senders/{senderKey}/templates")
	Object readTemplate(
		@PathVariable("appkey") String appKey,
		@PathVariable("senderKey") String sendKey
	);
}
