package co.kr.woojjam.concurrency.coupon;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import co.kr.woojjam.concurrency.config.TestDataBaseConfig;
import co.kr.woojjam.concurrency.entity.TestCoupon;
import co.kr.woojjam.concurrency.entity.TestHistory;
import co.kr.woojjam.concurrency.entity.TestUser;
import co.kr.woojjam.concurrency.repository.TestCouponRepository;
import co.kr.woojjam.concurrency.repository.TestHistoryRepository;
import co.kr.woojjam.concurrency.repository.TestUserRepository;
import co.kr.woojjam.concurrency.service.TestCouponService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile("local")
@SpringBootTest
public class TestCouponLockTest {

	@Autowired
	private TestCouponService testCouponService;
	@Autowired
	private TestCouponRepository testCouponRepository;
	@Autowired
	private TestHistoryRepository testHistoryRepository;

	@Autowired
	private TestUserRepository testUserRepository;

	private TestCoupon testCoupon;
	private TestUser testUser;

	@BeforeEach
	void init() {
		testCoupon = TestCoupon.builder()
			.code("A")
			.stock(20)
			.build();

		TestUser user = TestUser.builder()
			.name("WooJJam")
			.build();

		testUserRepository.save(user);
		testCouponRepository.save(testCoupon);
	}

	@Test
	@DisplayName("100명의 유저가 A 쿠폰 20장을 동시에 사용할려고 경쟁하는 상황")
	void executeCoupon() throws InterruptedException {

		final int people = 100;
		final Long couponId = 1L;
		final Long userId = 1L;
		final CountDownLatch countDownLatch = new CountDownLatch(people);

		List<Thread> workers = Stream
			.generate(() -> new Thread(new LockWorker(couponId, userId, countDownLatch)))
			.limit(people)
			.toList();

		workers.forEach(Thread::start);
		countDownLatch.await();

		List<TestHistory> results = testHistoryRepository.findAll();

		assertThat(results.size()).isEqualTo(20);

	}

	@Test
	@DisplayName("격리 수준으로 동시성 제어하기")
	void executeCouponWithIsolationLevel() throws InterruptedException {

		final int people = 100;
		final Long couponId = 1L;
		final Long userId = 1L;
		final CountDownLatch countDownLatch = new CountDownLatch(people);

		List<Thread> workers = Stream
			.generate(() -> new Thread(new LockWorker(couponId, userId, countDownLatch)))
			.limit(people)
			.toList();

		workers.forEach(Thread::start);
		countDownLatch.await();

		List<TestHistory> results = testHistoryRepository.findAll();

		assertThat(results.size()).isEqualTo(20);

	}

	private class LockWorker implements Runnable {

		private Long couponId;
		private Long userId;
		private CountDownLatch countDownLatch;

		public LockWorker(final Long couponId, final Long userId, final CountDownLatch countDownLatch) {
			this.couponId = couponId;
			this.userId = userId;
			this.countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			try {
				testCouponService.useCouponWithIsolationLevel(couponId, userId);
			} catch (Exception e) {
				log.info("재고 부족");
			} finally {
				countDownLatch.countDown();
			}
		}
	}

}
