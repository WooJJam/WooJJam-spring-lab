package co.kr.woojjam.kakao.bizmessage.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "co.kr.woojjam.kakao")
public class FeignConfig {
}
