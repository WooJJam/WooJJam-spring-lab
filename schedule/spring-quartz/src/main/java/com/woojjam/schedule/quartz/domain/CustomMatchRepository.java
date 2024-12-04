package com.woojjam.schedule.quartz.domain;

import java.util.List;

import com.woojjam.schedule.quartz.domain.Match;

public interface CustomMatchRepository {

	List<Match> findMatch();
}
