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
		if (200 == HttpStatus.OK.value()) {
			boolean status = environment.matchesProfiles("blue");
			if (status) {
				return Health.up().withDetail("data", "blue").build();
			}
			return Health.up().withDetail("data", "green").build();
		}

		return Health.down().build();
	}
}
