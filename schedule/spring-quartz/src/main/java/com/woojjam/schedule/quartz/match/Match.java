package com.woojjam.schedule.quartz.match;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "TestMatch")
@ToString
public class Match {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int total;
	private int remain;
	private LocalDateTime startAt;
	private LocalDateTime endAt;

	@Builder
	public Match(final Long id, final int total, final int remain, final LocalDateTime startAt,
		final LocalDateTime endAt) {
		this.id = id;
		this.total = total;
		this.remain = remain;
		this.startAt = startAt;
		this.endAt = endAt;
	}
}
