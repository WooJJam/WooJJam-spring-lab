package com.woojjam.schedule.springscheduler.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long>, CustomMatchRepository {
}
