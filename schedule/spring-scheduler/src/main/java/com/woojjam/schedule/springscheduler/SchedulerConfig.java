package com.woojjam.schedule.springscheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.woojjam.schedule.springscheduler.domain.Match;
import com.woojjam.schedule.springscheduler.domain.MatchService;
import com.woojjam.schedule.springscheduler.domain.User;

import lombok.RequiredArgsConstructor;

@Component
@EnableScheduling
@Transactional
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

	@Scheduled(cron = "0 * * * * *")
	public void cancelMatch() {
		log.info("This log is cancelMatch Method {}", dateFormat.format(new Date()));
		List<Match> match = matchService.findMatch();
		for (Match m : match) {
			log.info("find Match = {}", m);
		}
	}
}
