package com.woojjam.schedule.quartz.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
public class User {

	@Id @GeneratedValue
	private Long id;

	private String username;

	@Builder
	public User(final Long id, final String username) {
		this.id = id;
		this.username = username;
	}
}
