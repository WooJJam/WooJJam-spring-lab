// package com.woojjam.schedule.springscheduler;
//
// import org.springframework.context.annotation.Configuration;
// import org.springframework.scheduling.annotation.EnableScheduling;
// import org.springframework.scheduling.annotation.SchedulingConfigurer;
// import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
// import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//
// @Configuration
// @EnableScheduling
// public class SchedulerConfigurer implements SchedulingConfigurer {
//
// 	@Override
// 	public void configureTasks(final ScheduledTaskRegistrar taskRegistrar) {
// 		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
//
// 		int threadCount = Runtime.getRuntime().availableProcessors();
// 		threadPoolTaskScheduler.setPoolSize(threadCount);
// 		threadPoolTaskScheduler.setThreadNamePrefix("my-scheduler-");
// 		threadPoolTaskScheduler.initialize();
// 		taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
// 	}
// }
