package co.kr.woojjam.asynchronous;

import static java.lang.Thread.*;
import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompletableFutureOrderCallbackTest {

	private static final String orderNo = "1234567";
	private Map<String, String> items = new HashMap<>();

	@BeforeEach
	void init() {
		items.put(orderNo, "iPhone 16");
	}

	@Test
	@DisplayName("thenApply로 콜백 조합하기")
	void thenApplyTest() throws ExecutionException, InterruptedException {
		CompletableFuture<String> orders = CompletableFuture.supplyAsync(() -> orderItem(orderNo));
		CompletableFuture<String> paymentInfo = orders.thenApply(this::getPaymentInfo);

		assertThat("주문 아이템: iPhone 16, 주문 가격: 1,300,000").isEqualTo(paymentInfo.get());
	}

	@Test
	@DisplayName("thenApplyAsync로 콜백 조합하기")
	void thenApplyAsyncTest() throws ExecutionException, InterruptedException {
		CompletableFuture<String> orders = CompletableFuture.supplyAsync(() -> orderItem(orderNo));
		CompletableFuture<String> applyPaymentInfo = orders.thenApply(this::getPaymentInfo);
		CompletableFuture<String> applyAsyncPaymentInfo = orders.thenApplyAsync(this::getPaymentInfo);

		assertThat("주문 아이템: iPhone 16, 주문 가격: 1,300,000").isEqualTo(applyPaymentInfo.get());
		assertThat("주문 아이템: iPhone 16, 주문 가격: 1,300,000").isEqualTo(applyAsyncPaymentInfo.get());

	}

	@Test
	@DisplayName("thenApplyAsync 커스텀 스레드 풀로 콜백 조합하기")
	void thenApplyAsyncExecutorTest() throws ExecutionException, InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(4);
		CompletableFuture<String> orders = CompletableFuture.supplyAsync(() -> orderItem(orderNo))
			.thenApplyAsync(this::getPaymentInfo, executor);

		assertThat("주문 아이템: iPhone 16, 주문 가격: 1,300,000").isEqualTo(orders.get());
	}

	@Test
	@DisplayName("thenAccept로 콜백 조합하기")
	void thenAcceptTest() throws ExecutionException, InterruptedException {
		CompletableFuture<Void> orderFuture = CompletableFuture.supplyAsync(() -> orderItem(orderNo))
			.thenAccept(this::printOrderItem);

		orderFuture.get();
	}

	@Test
	@DisplayName("thenRun 콜백 조합하기")
	void thenRunTest() throws ExecutionException, InterruptedException {
		CompletableFuture<Void> orderFuture = CompletableFuture.supplyAsync(() -> orderItem(orderNo))
			.thenRun(this::printOrderLog);

		orderFuture.get();
	}

	/**
	 * 사용자 정보, 주문 내역, 결제 상태를 각각 비동기로 조회하고 모두 완료되었을 때 통합 응답 생성
	 */
	@Test
	@DisplayName("allOf 콜백 조합하기")
	void allOfTest() throws Exception{

		CompletableFuture<String> userFuture = CompletableFuture.supplyAsync(() -> {
			try {
				sleep(2000);
				System.out.println("userFuture");
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			return "사용자: WooJJam";
		});

		CompletableFuture<String> orderFuture = CompletableFuture.supplyAsync(() -> {
			try {
				sleep(2000);
				System.out.println("orderFuture");
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			return "주문: 아이폰 16";
		});

		CompletableFuture<String> paymentFuture = CompletableFuture.supplyAsync(() -> {
			try {
				sleep(1000);
				System.out.println("paymentFuture");
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			return "결제: 완료";
		});

		CompletableFuture<Void> allFuture = CompletableFuture.allOf(userFuture, orderFuture, paymentFuture);
		allFuture.get();

		assertThat(userFuture.isDone()).isTrue();
		assertThat(orderFuture.isDone()).isTrue();
		assertThat(paymentFuture.isDone()).isTrue();

		String result = Stream.of(userFuture, orderFuture, paymentFuture)
			.map(CompletableFuture::join)
			.collect(Collectors.joining(" "));

		assertThat("사용자: WooJJam 주문: 아이폰 16 결제: 완료").isEqualTo(result);
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

	private void printOrderItem(final String orderNo) {
		try {
			sleep(2000);
			System.out.println("orderItem Thread = " + Thread.currentThread().getName());
		} catch (InterruptedException e) {
			System.out.println(e.getCause().getMessage());
		}

		System.out.println("주문 아이템: iPhone 16, 주문 가격: 1,300,000");
	}

	private String getPaymentInfo(final String item) {
		try {
			sleep(2000);
			System.out.println("getPaymentInfo Thread = " + Thread.currentThread().getName());
		} catch (InterruptedException e) {
			System.out.println(e.getCause().getMessage());
		}
		return String.format("주문 아이템: %s, 주문 가격: 1,300,000", item);
	}

	private void printOrderLog() {
		System.out.println("새로운 아이템을 구매하였습니다.");
	}

}
