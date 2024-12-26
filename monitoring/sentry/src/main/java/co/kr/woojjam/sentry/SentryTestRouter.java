package co.kr.woojjam.sentry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.sentry.Sentry;
import io.sentry.SentryLevel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/sentry-test")
public class SentryTestRouter {

	@GetMapping
	public void test(@RequestBody SentryRequest request) {
		log.info("test request = {}", request);
		if (request.name().length() < 3) {
			Sentry.captureMessage("name length less than 3", SentryLevel.INFO);
			Sentry.captureException(new IllegalArgumentException("This name length less than 3"));
		}
		Sentry.captureException(new RuntimeException("This name length longer than 3"));
	}
}
