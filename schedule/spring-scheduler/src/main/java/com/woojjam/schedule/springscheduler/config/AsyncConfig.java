package com.woojjam.schedule.springscheduler.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

	@Bean(name = "schedulerTaskExecutor")
	public ThreadPoolTaskExecutor taskExecutor() {
		int threadCount = 10;
		int corePoolSize = Math.max(2, threadCount / 2);

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(threadCount);
		executor.setQueueCapacity(corePoolSize * 2);
		executor.setThreadNamePrefix("AsyncThread-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();
		return executor;
	}

	@Bean(name = "lowThreadTaskExecutor")
	public ThreadPoolTaskExecutor defaultTaskExecutor() {
		int threadCount = 1;
		int corePoolSize = 1;

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(threadCount);
		executor.setQueueCapacity(corePoolSize * 2);
		executor.setThreadNamePrefix("DefaultTaskExecutor-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();
		return executor;
	}
}
