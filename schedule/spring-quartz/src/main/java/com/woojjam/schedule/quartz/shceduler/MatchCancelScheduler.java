package com.woojjam.schedule.quartz.shceduler;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


@Configuration
public class MatchCancelScheduler {

	@Bean
	public JobDetail matchJobDetail() {
		return JobBuilder.newJob().ofType(MatchJob.class)
			.withIdentity("MatchCancelJobDetail", "woojjam")
			.withDescription("매치 시작 2시간전 인원 미달시 매치를 취소합니다.")
			.storeDurably()
			.build();
	}

	@Bean
	public Trigger matchJobTrigger(JobDetail matchJobDetail) {
		return TriggerBuilder.newTrigger()
			.forJob(matchJobDetail)
			.withIdentity("MatchCancelTrigger", "woojjam")
			.startNow()
			.withSchedule(SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(10)
				.repeatForever())
			.build();
	}

	@Bean
	public Scheduler scheduler(JobDetail matchJobDetail, Trigger matchJobTrigger, SchedulerFactoryBean factory) throws
		SchedulerException {
		JobKey jobKey = matchJobDetail.getKey();

		Scheduler scheduler = factory.getScheduler();
		scheduler.deleteJob(jobKey);

		scheduler.scheduleJob(matchJobDetail, matchJobTrigger);
		scheduler.start();
		return scheduler;

	}

}


