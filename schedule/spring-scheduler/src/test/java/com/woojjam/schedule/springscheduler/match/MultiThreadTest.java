package com.woojjam.schedule.springscheduler.match;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.woojjam.schedule.springscheduler.domain.Match;
import com.woojjam.schedule.springscheduler.domain.MatchStatus;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MultiThreadTest {

	@Test
	@Order(1)
	public void 스레드가_1개일때_1000개의_매치_업데이트() throws Exception {
		testUpdateCancelMatch(1, 1000); // 스레드 1개로 테스트
	}

	@Test
	@Order(2)
	public void 스레드가_10개일때_1000개의_매치_업데이트() throws Exception {
		testUpdateCancelMatch(10, 1000); // 스레드 10개로 테스트
	}

	@Test
	@Order(3)
	public void 스레드가_100개일때_1000개의_매치_업데이트() throws Exception {
		testUpdateCancelMatch(100, 1000); // 스레드 10개로 테스트
	}


	private void testUpdateCancelMatch(int threadCount, int matchCount) throws Exception{
	    //given
		List<Match> matches = new ArrayList<>();
		for (int i = 1; i <= matchCount; i++) {
			Match match = Match.builder()
				.id((long) (i))
				.total(10)
				.remain(1)
				.matchStatus(MatchStatus.WAITING)
				.build();
			matches.add(match);
		}

		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		CountDownLatch latch = new CountDownLatch(matchCount);

		long startTime = System.currentTimeMillis();
		//when
		for (Match match : matches) {
			executorService.submit(() -> {
				try {
					match.updateCancelMatch();
					Thread.sleep(10);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executorService.shutdown();

		long endTime = System.currentTimeMillis(); // 종료 시간 기록
		System.out.println("Threads: " + threadCount + ", Time taken: " + (endTime - startTime) + " ms");

	    //then
		for (Match match : matches) {
			Assertions.assertEquals(MatchStatus.CANCELLED, match.getMatchStatus());
		}
	}
}
