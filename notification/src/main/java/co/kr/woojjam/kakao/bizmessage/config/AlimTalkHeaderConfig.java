package co.kr.woojjam.kakao.bizmessage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class AlimTalkHeaderConfig {

	@Value("${KAKAO_BIZ_MESSAGE_SECRET_KEY}")
	private String secretKEy;

	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			requestTemplate.header("X-Secret-Key", secretKEy);
		};
	}
}
