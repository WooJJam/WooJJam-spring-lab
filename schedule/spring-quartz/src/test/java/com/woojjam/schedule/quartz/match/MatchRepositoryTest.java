package com.woojjam.schedule.quartz.match;


import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MatchRepositoryTest {

	@Autowired
	private MatchRepository matchRepository;


	@BeforeEach
	public void setUp() throws Exception {
		LocalDateTime startAt = LocalDateTime.now();
		LocalDateTime endAt = startAt.plusHours(2);
		Match match = Match.builder()
			.total(10)
			.remain(0)
			.startAt(startAt.plusMinutes(30))
			.endAt(endAt.plusMinutes(30))
			.build();
		matchRepository.save(match);

		Match match2 = Match.builder()
			.total(10)
			.remain(2)
			.startAt(startAt)
			.endAt(endAt)
			.build();
		matchRepository.save(match2);
    }
	@Test
	public void 매치_시작_2시간전_조회() throws Exception {

		List<Match> findMatch = matchRepository.findMatch();

		assertThat(findMatch.size()).isEqualTo(1);
	}

}
