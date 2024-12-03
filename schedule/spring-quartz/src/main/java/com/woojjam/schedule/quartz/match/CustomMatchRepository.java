package com.woojjam.schedule.quartz.match;

import java.util.List;

public interface CustomMatchRepository {

	List<Match> findMatch();
}
