package com.woojjam.schedule.quartz.shceduler;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.woojjam.schedule.quartz.domain.Match;
import com.woojjam.schedule.quartz.domain.MatchRepository;
import com.woojjam.schedule.quartz.domain.MatchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MatchJob implements Job {

	@Autowired
	private MatchService matchService;


	// @Override
	// protected void executeInternal(final JobExecutionContext context) throws JobExecutionException {
	// 	log.info("MatchJop Starting");
	// 	matchService.findMatch().forEach(match -> log.info("match = {}", match.toString()));
	// }

	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {
		log.info("MatchJop Starting");
		List<Match> findMatch = matchService.findMatch();
		for (Match match : findMatch) {
			log.info("match = {}", match);
		}
	}
}
