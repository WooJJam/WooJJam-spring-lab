package co.kr.woojjam.concurrency.coupon;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestCouponService {

	private final TestCouponRepository testCouponRepository;

	@Transactional
	public void useCoupon(Long couponId) {
		TestCoupon coupon = testCouponRepository.findById(couponId)
			.orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));
		coupon.useCoupon();
		log.info("coupon = {}", coupon);
	}

	public TestCoupon read(Long id) {
		return testCouponRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다"));
	}
}
