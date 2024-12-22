package co.kr.woojjam.infrastructure.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomHealthIndicator implements HealthIndicator {

	private final Environment environment;

	@Override
	public Health getHealth(final boolean includeDetails) {
		return HealthIndicator.super.getHealth(includeDetails);
	}

	@Override
	public Health health() {
		String port = environment.getProperty("local.server.port");
		return Health.up()
			.withDetail("data", port).build();
	}
}
