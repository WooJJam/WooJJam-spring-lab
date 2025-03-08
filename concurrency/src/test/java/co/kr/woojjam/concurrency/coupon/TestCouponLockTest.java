package co.kr.woojjam.concurrency.coupon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import co.kr.woojjam.concurrency.config.TestDataBaseConfig;

@SpringBootTest(properties = {"spring.jpa.hibernate.ddl-auto=create"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// @ContextConfiguration(classes = TestJpaConfig.class)
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
			.stock(100)
			.build();

		testCouponRepository.save(testCoupon);
	}

	@Test
	public void 쿠폰_차감_테스트() throws Exception{
		testCouponService.useCoupon(1L);

		TestCoupon coupon = testCouponService.read(1L);
	}

}
