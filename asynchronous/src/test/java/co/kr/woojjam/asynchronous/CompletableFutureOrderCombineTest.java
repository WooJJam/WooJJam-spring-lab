package co.kr.woojjam.asynchronous;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompletableFutureOrderCombineTest {

	private static final String orderNo = "1234567";
	private static final String optionNo = "2222";
	private Map<String, String> items = new HashMap<>();

	@BeforeEach
	void init() {
		items.put(orderNo, "iPhone 16");
	}

	@Test
	@DisplayName("thenCompose 으로 작업 결합하기")
	void thenComposeTest() throws ExecutionException, InterruptedException {
		CompletableFuture<String> orderFuture = CompletableFuture.supplyAsync(() -> orderItem(orderNo));
		CompletableFuture<String> result = orderFuture.thenCompose(
			item -> CompletableFuture.supplyAsync(() -> getPaymentInfo(item)));

		assertThat("주문 아이템: iPhone 16/ 결제 정보: 신용카드 1,200,000원").isEqualTo(result.get());
	}

	@Test
	@DisplayName("thenCombine 으로 작업 결합하기")
	void thenCombineTest() throws ExecutionException, InterruptedException {
		CompletableFuture<String> orderFuture = CompletableFuture.supplyAsync(() -> orderItem(orderNo));
		CompletableFuture<String> optionFuture = CompletableFuture.supplyAsync(this::getProductInfo);

		CompletableFuture<String> result = orderFuture.thenCombine(optionFuture,
			this::combineOrderAndProductInfo);


		assertThat("주문 아이템: iPhone 16/ [옵션] AppleCare+").isEqualTo(result.get());
	}

	private String orderItem(final String orderNo) {
		try {
			Thread.sleep(1000);
			System.out.println("orderItem Thread = " + Thread.currentThread().getName());
		} catch (InterruptedException e) {
			System.out.println(e.getCause().getMessage());
		}
		return String.format("주문 아이템: %s", items.get(orderNo));
	}

	private String getPaymentInfo(final String orderItem) {
		try {
			Thread.sleep(2000);
			System.out.println("getPaymentInfo Thread = " + Thread.currentThread().getName());
		} catch (InterruptedException e) {
			System.out.println(e.getCause().getMessage());
		}
		return orderItem + "/ 결제 정보: 신용카드 1,200,000원";
	}

	private String getProductInfo() {
		return "AppleCare+";
	}

	private String combineOrderAndProductInfo(String orderNo, String product) {
		return orderNo + "/ [옵션] " + product;
	}

}
