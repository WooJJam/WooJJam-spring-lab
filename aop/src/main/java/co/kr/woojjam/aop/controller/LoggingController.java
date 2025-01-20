package co.kr.woojjam.aop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.kr.woojjam.aop.dto.request.TestLoggingReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class LoggingController {

	@GetMapping("/logging")
	public void aopTestLogging(@RequestBody TestLoggingReq request) {
		log.info("Test Logging");
	}



}
