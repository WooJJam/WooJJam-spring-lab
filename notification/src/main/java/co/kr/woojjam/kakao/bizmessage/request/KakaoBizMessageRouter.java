package co.kr.woojjam.kakao.bizmessage.request;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.kr.woojjam.kakao.bizmessage.client.AlimTalkSupport;
import co.kr.woojjam.labsdomain.domains.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class KakaoBizMessageRouter {
	
	private final AlimTalkSupport alimTalkSupport;
	private final UserReader userReader;

	@PostMapping("/send")
	public Object sendMessage() {
		User user = userReader.read(1L);
		return alimTalkSupport.sendMessage(user);
	}
	
	@GetMapping("/message-templates")
	public void readTemplate() {
		alimTalkSupport.readTemplate();
	}

}
