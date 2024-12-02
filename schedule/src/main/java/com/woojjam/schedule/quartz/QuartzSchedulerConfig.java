package com.woojjam.schedule.quartz;

import org.quartz.SchedulerException;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class QuartzSchedulerConfig {

	private final LoggerQuartz loggerQuartz;

	@PostConstruct
	private void jobProgress() throws SchedulerException {
		loggerQuartz.run();
	}
}
