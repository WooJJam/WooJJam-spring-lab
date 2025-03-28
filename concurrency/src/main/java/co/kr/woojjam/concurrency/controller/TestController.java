package co.kr.woojjam.concurrency.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.kr.woojjam.concurrency.entity.TestHistory;
import co.kr.woojjam.concurrency.service.SynchronizedFacade;
import co.kr.woojjam.concurrency.service.TestCouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupon")
public class TestController {

	private final SynchronizedFacade synchronizedFacade;
	private final TestCouponService testCouponService;

	@PostMapping("/init")
	public void init() {
		synchronizedFacade.init();
	}

	@PostMapping("/use")
	public ResponseEntity<?> useCoupon() {
		log.info("쿠폰을 사용합니다.");
		TestHistory history = synchronizedFacade.useCouponWithSynchronized(1L, 1L);
		return ResponseEntity.ok().body(history);
	}

	@PostMapping("/use-multi-server")
	public ResponseEntity<?> useCouponWithMultiServer() {
		log.info("쿠폰을 사용합니다.");
		TestHistory history = testCouponService.useCouponWithPessimisticLock(1L, 1L);
		return ResponseEntity.ok().body(history);
	}
}
