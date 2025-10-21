package co.kr.woojjam.asynchronous.part1;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompletableFutureOrderTest {

	private static final String orderNo = "1234567";
	private Map<String, String> items = new HashMap<>();

	@BeforeEach
	void init() {
		items.put(orderNo, "iPhone 16");
	}


	@Test
	@DisplayName("runAsync로 주문 정보 실행 테스트")
	void orderItemAsyncTest() throws ExecutionException, InterruptedException {
		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			orderItem(orderNo);
		});

		future.get();
	}

	@Test
	@DisplayName("supplyAsync로 주문 정보 실행 테스트")
	void orderItemSupplyAsyncTest() throws ExecutionException, InterruptedException {

		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> orderItem(orderNo));

		assertThat(future.get()).isEqualTo("iPhone 16");
	}

	private String orderItem(final String orderNo) {
		try {
			Thread.sleep(2000); // orderInfoRepository.findByOrderNo(orderNo);
		} catch (InterruptedException e) {
			System.out.println(e.getCause().getMessage());
		}
		return items.get(orderNo);
	}
}
