package com.woojjam.schedule.springscheduler.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MatchStatus {

	WAITING("대기중인 매치"),
	FULL("인원이 가득찬 매치"),
	FINISH("종료된 매치"),
	CANCELLED("취소된 매치");

	private final String name;
}
