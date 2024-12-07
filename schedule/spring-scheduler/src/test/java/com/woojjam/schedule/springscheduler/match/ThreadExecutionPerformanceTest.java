package com.woojjam.schedule.springscheduler.match;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.woojjam.schedule.springscheduler.config.AsyncConfig;

@SpringJUnitConfig(classes = AsyncConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ThreadExecutionPerformanceTest {
	@Autowired
	@Qualifier("schedulerTaskExecutor")
	private ThreadPoolTaskExecutor schedulerTaskExecutor;

	@Autowired
	@Qualifier("lowThreadTaskExecutor")
	private ThreadPoolTaskExecutor lowThreadTaskExecutor;

	private final int taskCounts = 50;

	private static final int ms = 100;
	private static final List<Integer> secs = new ArrayList<>();

	@BeforeAll
	public static void init() {
		Random random = new Random();
		for (int i = 0; i < 50; i++) {
			secs.add(random.nextInt(2000) + ms); // 100ms ~ 2100ms 사이 랜덤 지연 시간
		}
		System.out.println("### 지연 시간 배열 생성 완료 ###");
	}

	@Test
	@Order(1)
	public void 동기_단일_스레드_작업_100개_테스트() throws InterruptedException {
		System.out.println("### 동기 작업 테스트 ###");
		long timeTaken = runSynchronousTest("동기 작업");
		System.out.println("동기 작업 총 소요 시간: " + timeTaken + " ms");
	}

	@Test
	@Order(2)
	public void 동기_멀티_스레드_작업_100개_테스트() throws InterruptedException {
		System.out.println("### 동기 멀티 스레드 테스트 ###");
		long timeTaken = runSynchronousMultiThreadTestSync("동기 멀티 스레드");
		System.out.println("동기 멀티 스레드 총 소요 시간: " + timeTaken + " ms");
	}


	@Test
	@Order(3)
	public void 비동기_단일_스레드_100개_병렬_테스트() throws InterruptedException {
		System.out.println("### 비동기 단일 스레드 병렬 테스트 ###");
		long timeTaken = runAsynchronousTest(lowThreadTaskExecutor, "제한된 스레드");
		System.out.println("제한된 스레드 총 소요 시간: " + timeTaken + " ms");
	}

	@Test
	@Order(4)
	public void 비동기_멀티_스레드_작업_100개_병렬_테스트() throws InterruptedException {
		System.out.println("### 비동기 멀티 스레드 병렬 테스트 ###");
		long timeTaken = runAsynchronousTest(schedulerTaskExecutor, "멀티 스레드");
		System.out.println("멀티 스레드 총 소요 시간: " + timeTaken + " ms");
	}

	private long runAsynchronousTest(ThreadPoolTaskExecutor executor, String taskName) throws InterruptedException {
		List<String> tasks = new ArrayList<>();
		for (int i = 1; i <= taskCounts; i++) {
			tasks.add(taskName + " 작업 " + i);
		}

		CountDownLatch latch = new CountDownLatch(taskCounts);
		AtomicInteger count = new AtomicInteger();
		long startTime = System.currentTimeMillis();

		for (int i = 0; i < taskCounts; i++) {
			final int index = i; // 고유 작업 인덱스
			final int delay = secs.get(index); // 지연 시간 배열에서 값 가져오기
			executor.execute(() -> {
				try {
					System.out.println(taskName + " - 실행 중, 작업 번호: " + index + ", 지연시간: " + delay + "ms");
					Thread.sleep(delay); // 작업 지연
					count.incrementAndGet();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		long endTime = System.currentTimeMillis();
		System.out.println(taskName + " - 처리된 작업 수: " + count.get());
		return endTime - startTime;
	}


	private long runSynchronousTest(String taskName) {
		List<String> tasks = new ArrayList<>();
		for (int i = 1; i <= taskCounts; i++) {
			tasks.add(taskName + " 작업 " + i);
		}

		long startTime = System.currentTimeMillis();

		for (int i = 0; i <taskCounts; i++) {
			final int delay = secs.get(i);
			try {
				System.out.println(taskName + " - 실행 중, "+"작업 번호: "+ i + " 지연시간: "+ delay);
				Thread.sleep(delay); // 작업 지연
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		long endTime = System.currentTimeMillis();
		System.out.println(taskName + " - 처리된 작업 수: " + tasks.size());
		return endTime - startTime;
	}

	private long runSynchronousMultiThreadTestSync(String taskName) throws InterruptedException {
		List<String> tasks = new ArrayList<>();
		for (int i = 1; i <= taskCounts; i++) {
			tasks.add(taskName + " 작업 " + i);
		}

		// 스레드 풀 생성
		ExecutorService executor = Executors.newFixedThreadPool(10);

		// 각 작업을 Callable로 변환
		List<Callable<Void>> callables = new ArrayList<>();
		for (int i = 0; i < taskCounts; i++) {
			final int index = i;
			final int delay = secs.get(index);
			callables.add(() -> {
				System.out.println(taskName + " - 실행 중, 작업 번호: " + index + ", 지연시간: " + delay + "ms");
				Thread.sleep(delay); // 작업 지연
				return null;
			});
		}

		long startTime = System.currentTimeMillis();

		// 모든 작업 동기 실행
		executor.invokeAll(callables); // 모든 작업이 완료될 때까지 대기

		executor.shutdown(); // 스레드 풀 종료
		long endTime = System.currentTimeMillis();

		return endTime - startTime;
	}

}
