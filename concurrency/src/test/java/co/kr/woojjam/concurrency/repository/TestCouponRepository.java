package co.kr.woojjam.concurrency.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.kr.woojjam.concurrency.entity.TestCoupon;

public interface TestCouponRepository extends JpaRepository<TestCoupon, Long> {
}
