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
import co.kr.woojjam.concurrency.entity.TestUser;
import co.kr.woojjam.concurrency.repository.TestCouponRepository;
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
	private TestUserRepository testUserRepository;

	private TestCoupon testCoupon;
	private TestUser testUser;

	@BeforeEach
	void init() {
		testCoupon = TestCoupon.builder()
			.code("A")
			.stock(0)
			.build();

		TestUser user = TestUser.builder()
			.name("WooJJam")
			.build();

		testUserRepository.save(user);
		testCouponRepository.save(testCoupon);
	}

	@Test
	@DisplayName("어떠한 동시성 제어도 적용하지 않은 기본 메소드")
	public void executeCoupon() throws InterruptedException {

		final int people = 2;
		final Long couponId = 1L;
		final Long userId = 1L;
		final CountDownLatch countDownLatch = new CountDownLatch(people);

		List<Thread> workers = Stream
			.generate(() -> new Thread(new LockWorker(couponId, userId, countDownLatch)))
			.limit(people)
			.toList();

		workers.forEach(Thread::start);
		countDownLatch.await();

		List<TestCoupon> results = testCouponRepository.findAll();

		results.forEach(result -> {
			System.out.println("results = " + results);
		});

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
			testCouponService.useCoupon(couponId, userId);
			countDownLatch.countDown();
		}
	}

}
