package com.woojjam.schedule.springscheduler.match;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.woojjam.schedule.springscheduler.config.AsyncConfig;
import com.woojjam.schedule.springscheduler.domain.Match;
import com.woojjam.schedule.springscheduler.domain.MatchStatus;

@SpringJUnitConfig(classes = AsyncConfig.class)
// @SpringBootTest
public class MatchSchedulerTest {

	@Autowired
	@Qualifier("schedulerTaskExecutor")
	private ThreadPoolTaskExecutor schedulerTaskExecutor;


	@Test
	public void 매치_스케줄러_멀티_쓰레드_테스트() throws Exception{
		List<Match> matches = new ArrayList<>();
		int totalMatches = 100;
		for (int i = 1; i <= totalMatches; i++) {
			matches.add(new Match((long) i, 10, 1, MatchStatus.WAITING, null, null)); // 초기 상태: isCancelled = false
		}

		// 2. CountDownLatch로 작업 추적
		CountDownLatch latch = new CountDownLatch(totalMatches);
		AtomicInteger count = new AtomicInteger();
		long startTime = System.currentTimeMillis();
		// 3. 병렬 작업 실행
		for (Match match : matches) {
			schedulerTaskExecutor.execute(() -> {
				try {
					System.out.println("Thread name: "+ schedulerTaskExecutor.getThreadNamePrefix() + ", Active Threads: " + schedulerTaskExecutor.getActiveCount());
					System.out.println("Thread name: "+ schedulerTaskExecutor.getThreadNamePrefix() + ", Queue Size: " + schedulerTaskExecutor.getThreadPoolExecutor().getQueue().size());
					match.updateCancelMatch(); // 매치 업데이트 작업
					count.incrementAndGet();
					Thread.sleep(100);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} finally {
					latch.countDown(); // 작업 완료 시 카운트 감소
				}
			});
		}

		// 4. 모든 작업이 완료될 때까지 대기
		latch.await();
		long endTime = System.currentTimeMillis();
		System.out.println("count = " + count.get());

		System.out.println("Total Time = "+ (endTime - startTime) +" ms");

		// 5. 결과 검증
		for (Match match : matches) {
			Assertions.assertEquals(match.getMatchStatus(), MatchStatus.CANCELLED);
		}

	}

}
