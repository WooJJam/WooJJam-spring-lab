// package com.woojjam.schedule.quartz;
//
// import org.quartz.JobBuilder;
// import org.quartz.JobDetail;
// import org.quartz.Scheduler;
// import org.quartz.SchedulerException;
// import org.quartz.SimpleScheduleBuilder;
// import org.quartz.Trigger;
// import org.quartz.TriggerBuilder;
// import org.quartz.impl.StdSchedulerFactory;
// import org.springframework.context.annotation.Configuration;
//
// @Configuration
// public class LoggerQuartz {
//
// 	public void run() throws SchedulerException {
// 		JobDetail job = JobBuilder
// 			.newJob(LoggerJob.class)
// 			.withIdentity("LoggerJobDetailKey", "woojjam") // context.getJobDetail().getKey();
// 			.withDescription("This is Logger Job")
// 			.ofType(LoggerJob.class)
// 			.requestRecovery(true)
// 			.usingJobData("name", "jaemin")
// 			.build();
//
// 		Trigger trigger = TriggerBuilder.newTrigger()
// 			.withIdentity("loggerTrigger")
// 			.startNow()
// 			.withSchedule(SimpleScheduleBuilder.simpleSchedule()
// 				.withIntervalInSeconds(10)
// 				.repeatForever())
// 			.build();
//
// 		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
// 		scheduler.getListenerManager().addJobListener(new LoggerJobListener());
// 		scheduler.start();
// 		scheduler.scheduleJob(job, trigger);
// 	}
//
// }
