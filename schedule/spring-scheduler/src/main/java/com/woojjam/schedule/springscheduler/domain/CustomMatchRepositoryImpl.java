package com.woojjam.schedule.springscheduler.domain;

import static com.woojjam.schedule.springscheduler.domain.QMatch.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomMatchRepositoryImpl implements CustomMatchRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public List<Match> findMatch() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime timeRange = now.plusMinutes(90);

		return jpaQueryFactory.selectFrom(match)
			.where(match.startAt.between(now, timeRange))
			.fetch();
	}
}
