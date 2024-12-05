package com.woojjam.schedule.springscheduler.config;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.woojjam.schedule.springscheduler.UserService;
import com.woojjam.schedule.springscheduler.domain.MatchService;
import com.woojjam.schedule.springscheduler.domain.User;

import lombok.RequiredArgsConstructor;

@Component
@Transactional
@EnableAsync
@RequiredArgsConstructor
public class SchedulerConfig {

	private final UserService userService;
	private final MatchService matchService;
	private static final Logger log = LoggerFactory.getLogger(SchedulerConfig.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(fixedDelay = 30000)
	public void logger1() {
		User user = userService.read(1L);
		log.info("This log is Logger1 Method Time : {}", dateFormat.format(new Date()));
		log.info("Find User Is : {}", user);
	}

	@Scheduled(initialDelay = 30000)
	public void logger2() {
		log.info("This log is Logger2 Method {}", dateFormat.format(new Date()));
	}

	@Scheduled(fixedRate = 30000)
	public void logger3() {
		log.info("This log is Logger3 Method {}", dateFormat.format(new Date()));
	}

	// @Scheduled(cron = "0 * * * * *")
	// public void cancelMatch() {
	// 	log.info("This log is cancelMatch Method {}", dateFormat.format(new Date()));
	// 	List<Match> match = matchService.findMatch();
	// 	for (Match m : match) {
	// 		log.info("Find Match = {}", m);
	// 		m.updateCancelMatch();
	// 	}
	// }

	/**
	 * taskA, taskB
	 * Scheduler Configurer을 설정하여 멀티 스레드로 스케줄링이 동작
	 * @throws InterruptedException
	 */

	@Scheduled(fixedRate = 1000) // 1초마다 taskA를 실행합니다.
	// @Async("schedulerTaskExecutor")
	public void taskA() throws InterruptedException {
		Thread.sleep(10000); // 10초 동안 일시 중단합니다.
		log.info("taskA - {} - {}", LocalDateTime.now(), Thread.currentThread().getName());
		log.info("Total Thread count = {}",Thread.activeCount());
	}

	@Scheduled(fixedRate = 1000) // 1초마다 taskB를 실행합니다.
	// @Async("schedulerTaskExecutor")
	public void taskB() {// taskB의 로직을 실행합니다.
		log.info("taskB - {} - {}", LocalDateTime.now(), Thread.currentThread().getName());
	}
}
