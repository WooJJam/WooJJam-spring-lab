package com.woojjam.schedule.springscheduler.domain;

import java.util.List;

public interface CustomMatchRepository {

	List<Match> findMatch();
}
