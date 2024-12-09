package com.woojjam.schedule.springscheduler.config;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.woojjam.schedule.springscheduler.UserService;
import com.woojjam.schedule.springscheduler.domain.Match;
import com.woojjam.schedule.springscheduler.domain.MatchService;
import com.woojjam.schedule.springscheduler.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Transactional
@Slf4j
@EnableScheduling
@EnableAsync
@RequiredArgsConstructor
public class SchedulerConfig {

	private final UserService userService;
	private final MatchService matchService;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(cron ="0 * * * * *")
	public void logger1() {
		// User user = userService.read(1L);
		log.info("This log is Logger1 Method Time : {}", dateFormat.format(new Date()));
	}

	@Scheduled(fixedRate = 1000)
	public void logger2() throws InterruptedException {
		log.info("This log is Logger2 Method {}", dateFormat.format(new Date()));
		Thread.sleep(3000);
	}

	@Scheduled(fixedRate = 30000)
	public void logger3() {
		log.info("This log is Logger3 Method {}", dateFormat.format(new Date()));
	}

	@Scheduled(cron = "0 * * * * *")
	public void cancelMatch() {
		log.info("This log is cancelMatch Method {}", dateFormat.format(new Date()));
		List<Match> match = matchService.findMatch();
		for (Match m : match) {
			log.info("Find Match = {}", m);
			m.updateCancelMatch();
		}
	}

	/**
	 * taskA, taskB
	 * Scheduler Configurer을 설정하여 멀티 스레드로 스케줄링이 동작
	 * @throws InterruptedException
	 */

	@Scheduled(fixedRate = 1000) // 1초마다 taskA를 실행합니다.
	@Async
	public void taskA() throws InterruptedException {
		Thread.sleep(10000); // 10초 동안 일시 중단합니다.
		log.info("taskA - {} - {}", LocalDateTime.now(), Thread.currentThread().getName());
	}

	@Scheduled(fixedRate = 1000) // 1초마다 taskB를 실행합니다.
	@Async
	public void taskB() {// taskB의 로직을 실행합니다.
		log.info("taskB - {} - {}", LocalDateTime.now(), Thread.currentThread().getName());
	}
}
