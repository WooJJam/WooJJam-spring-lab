package com.woojjam.schedule.quartz.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
public class MatchReservation {

	@Id @GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Match match;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@Builder
	public MatchReservation(final Long id, final Match match, final User user) {
		this.id = id;
		this.match = match;
		this.user = user;
	}
}
