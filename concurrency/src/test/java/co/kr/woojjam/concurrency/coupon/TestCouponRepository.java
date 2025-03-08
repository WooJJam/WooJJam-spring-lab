package co.kr.woojjam.concurrency.coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCouponRepository extends JpaRepository<TestCoupon, Long> {
}
