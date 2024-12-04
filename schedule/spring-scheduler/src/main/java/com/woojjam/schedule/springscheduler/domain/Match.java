package com.woojjam.schedule.springscheduler.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int total;
	private int remain;

	@Enumerated(EnumType.STRING)
	private MatchStatus matchStatus;
	private LocalDateTime startAt;
	private LocalDateTime endAt;

	@Builder
	public Match(final Long id, final int total, final int remain, final MatchStatus matchStatus, final LocalDateTime startAt,
		final LocalDateTime endAt) {
		this.id = id;
		this.total = total;
		this.remain = remain;
		this.matchStatus = matchStatus;
		this.startAt = startAt;
		this.endAt = endAt;
	}

	public void updateCancelMatch() throws InterruptedException {
		// Thread.sleep(1000);
		this.matchStatus = MatchStatus.CANCELLED;
	}
}
