package co.kr.woojjam.schedule.springscheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@EnableScheduling
@Transactional
@RequiredArgsConstructor
public class SchedulerConfig {
	private static final Logger log = LoggerFactory.getLogger(SchedulerConfig.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(initialDelay = 10000)
	public void logger1() {
		log.info("This log is Logger1 Method {}", dateFormat.format(new Date()));
	}

	@Scheduled(fixedRate = 30000)
	public void logger2() {
		log.info("This log is Logger2 Method {}", dateFormat.format(new Date()));
	}
}
