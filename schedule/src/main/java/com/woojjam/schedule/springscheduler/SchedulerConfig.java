package com.woojjam.schedule.springscheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.woojjam.schedule.User;
import com.woojjam.schedule.UserService;

import lombok.RequiredArgsConstructor;

@Component
@EnableScheduling
@Transactional
@RequiredArgsConstructor
public class SchedulerConfig {

	private final UserService userService;
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

	@Scheduled(cron = "0 * * * * *")
	public void logger4() {
		log.info("This log is Logger4 Method {}", dateFormat.format(new Date()));
		User user = userService.read(1L);
		user.updateUsername("cron update username");
	}
}
