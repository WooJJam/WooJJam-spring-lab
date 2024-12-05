package com.woojjam.schedule.springscheduler.config;

import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.woojjam.schedule.springscheduler.domain.Match;
import com.woojjam.schedule.springscheduler.domain.MatchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Transactional
@EnableAsync
@Slf4j
@RequiredArgsConstructor
public class MatchScheduler {

	private final MatchService matchService;

	@Async("schedulerTaskExecutor")
	@Scheduled(fixedDelay = 10000)
	public void findMatchScheduler() throws InterruptedException {
		log.info("findMatchScheduler start");
		long startTime = System.currentTimeMillis();
		List<Match> match = matchService.findMatch();
		for (Match match1 : match) {
			match1.updateCancelMatch();
		}
		long endTime = System.currentTimeMillis(); // 종료 시간 기록
		log.info("Time taken: {} ms", (endTime - startTime));
	}
}
