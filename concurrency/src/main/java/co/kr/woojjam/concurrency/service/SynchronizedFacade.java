package co.kr.woojjam.concurrency.service;

import org.springframework.stereotype.Component;

import co.kr.woojjam.concurrency.entity.TestHistory;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;

@Component
@RequiredArgsConstructor
public class SynchronizedFacade {

	private final TestCouponService testCouponService;

	public void init() {
		testCouponService.init();
	}

	@Synchronized
	public TestHistory useCouponWithSynchronized(final Long couponId, final Long userId) {
		return testCouponService.useCoupon(couponId, userId);
	}
}
