package com.woojjam.schedule.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class LoggerJob implements Job{
	@Override
	public void execute(final JobExecutionContext jobExecutionContext) throws JobExecutionException {
		log.info("LoggerJob Starting");
	}
}
