package com.woojjam.schedule.quartz.domain;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatchService {

	private final MatchRepository matchRepository;
	private final UserRepository userRepository;
	private final MatchReservationRepository matchReservationRepository;

	// @PostConstruct
	@Transactional
	@PostConstruct
	public void init() {
		User user = User.builder()
			.username("jaemin")
			.build();
		userRepository.save(user);

		LocalDateTime startAt = LocalDateTime.now();
		LocalDateTime endAt = startAt.plusHours(2);
		Match match = Match.builder()
			.total(10)
			.remain(0)
			.startAt(startAt)
			.endAt(endAt)
			.build();
		matchRepository.save(match);

		Match match2 = Match.builder()
			.total(10)
			.remain(2)
			.startAt(startAt)
			.endAt(endAt)
			.build();
		matchRepository.save(match2);

		MatchReservation matchReservation1 = MatchReservation.builder()
			.match(match2)
			.user(user)
			.build();

		matchReservationRepository.save(matchReservation1);

	}
}
