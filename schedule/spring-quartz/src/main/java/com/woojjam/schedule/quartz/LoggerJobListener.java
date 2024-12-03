// package com.woojjam.schedule.quartz;
//
// import org.quartz.JobExecutionContext;
// import org.quartz.JobExecutionException;
// import org.quartz.JobListener;
//
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// public class LoggerJobListener implements JobListener {
// 	@Override
// 	public String getName() {
// 		return "LoggerJobListener";
// 	}
//
// 	@Override
// 	public void jobToBeExecuted(final JobExecutionContext jobExecutionContext) {
// 		log.info("Logger Job이 실행되기전 수행됩니다.");
// 	}
//
// 	@Override
// 	public void jobExecutionVetoed(final JobExecutionContext jobExecutionContext) {
// 		log.info("Logger Job이 실행 취소된 시점 수행됩니다.");
// 	}
//
// 	@Override
// 	public void jobWasExecuted(final JobExecutionContext jobExecutionContext, final JobExecutionException e) {
// 		log.info("Logger Job이 실행 완료된 시점 수행됩니다.");
// 	}
// }
