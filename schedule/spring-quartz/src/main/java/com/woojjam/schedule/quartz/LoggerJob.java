package com.woojjam.schedule.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class LoggerJob implements Job{
	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {
		log.info("LoggerJob Starting");
		JobKey key = context.getJobDetail().getKey();
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

		String name = jobDataMap.getString("name");

		log.info("Instance "+ key + " of name: "+ name);;
	}
}
