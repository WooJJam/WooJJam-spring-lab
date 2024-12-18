package co.kr.woojjam.kakao.bizmessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController("/api/v1")
public class KakaoBizMessageController {

	@PostMapping("/alimtalk/v2.3/appkeys/{appkey}/messages")
	public void sendMessage(
		@PathVariable("appKey") @Value("${APP_KEY}") final String appkey
	) {

	}

}
