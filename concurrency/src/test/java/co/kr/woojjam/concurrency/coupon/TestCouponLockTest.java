package co.kr.woojjam.concurrency.coupon;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.util.StopWatch;

import co.kr.woojjam.concurrency.config.TestDataBaseConfig;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(properties = {"spring.jpa.hibernate.ddl-auto=create"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestCouponLockTest extends TestDataBaseConfig {

	@Autowired
	private TestCouponService testCouponService;
	@Autowired
	private TestCouponRepository testCouponRepository;

	private TestCoupon testCoupon;

	@BeforeEach
	void init() {
		testCoupon = TestCoupon.builder()
			.code("A")
			.discountAmount(1000)
			.stock(2)
			.build();

		testCouponRepository.save(testCoupon);
	}

	@Test
	public void 낙관적락_유리한_케이스_테스트() throws InterruptedException {
		int users = 5;
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		CountDownLatch countDownLatch = new CountDownLatch(5);
		AtomicInteger success = new AtomicInteger(0);
		AtomicInteger failed = new AtomicInteger(0);

		long startTime = System.currentTimeMillis();

		for (int i = 0; i < users; i++) {
			executorService.submit(() -> {
				while(true) {
					try {
						testCouponService.useCoupon(1L);
						success.incrementAndGet();
						break;
					} catch (ObjectOptimisticLockingFailureException e) {
						log.warn("동시성 충돌 발생");
					} catch (IllegalStateException e) {
						log.info("{}",e.getMessage());
						failed.incrementAndGet();
						break;
					}
				}
				countDownLatch.countDown();
			});
		}

		countDownLatch.await(); // 모든 스레드가 끝날 때까지 대기
		long endTime = System.currentTimeMillis();

		System.out.println("낙관적 락 테스트 실행 시간: " + (endTime - startTime) + "ms");

		TestCoupon coupon = testCouponService.read(1L);
		assertThat(coupon.getStock()).isEqualTo(0);
		assertThat(success.get()).isEqualTo(2);
		assertThat(failed.get()).isEqualTo(3);
	}

}
