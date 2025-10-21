package co.kr.woojjam.asynchronous.part1;

import static java.lang.Thread.*;
import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompletableFutureOrderExceptionTest {

	private static final String orderNo = "1234567";
	private Map<String, String> items = new HashMap<>();

	@BeforeEach
	void init() {
		items.put(orderNo, "iPhone 16");
	}

	@Test
	@DisplayName("handle 테스트")
	void handleTest() throws ExecutionException, InterruptedException {

		CompletableFuture<String> orderFuture = CompletableFuture.supplyAsync(() -> {
			if (orderNo == null) {
				throw new IllegalArgumentException("존재하지 않는 주문번호 입니다.");
			}

			return orderItem(orderNo);
		}).handle((result, exception) -> exception == null ? result : "Default String");

		assertThat("iPhone 16").isEqualTo(orderFuture.get());
	}

	private String orderItem(final String orderNo) {
		try {
			sleep(2000);
			System.out.println("orderItem Thread = " + Thread.currentThread().getName());
		} catch (InterruptedException e) {
			System.out.println(e.getCause().getMessage());
		}
		return items.get(orderNo);
	}
}
