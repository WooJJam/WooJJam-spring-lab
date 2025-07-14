package co.kr.woojjam.asynchronous.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FutureUserDashboardExampleTest {

	@Test
	@DisplayName("Future로 대시보드 기능 완성하기")
	void FutureTest() throws ExecutionException, InterruptedException {

		ExecutorService executor = Executors.newFixedThreadPool(2);

		long startTime = System.currentTimeMillis();

		Future<String> userFuture = executor.submit(() -> {
			Thread.sleep(2000);
			return "WooJJam(25세)";
		});

		Future<String> orderFuture = executor.submit(() -> {
			Thread.sleep(1000);
			return "- Book\n- pen";
		});

		System.out.println("2개의 요청을 병렬로 실행하였습니다.");

		String user = userFuture.get();
		String orders = orderFuture.get();

		System.out.println("\n📋 사용자 대시보드 정보:");
		System.out.println("User: " + user);
		System.out.println("Recent Orders:\n" + orders);

		long endTime = System.currentTimeMillis();
		System.out.println("\n총 소요 시간: " + (endTime - startTime) + "ms");

		executor.shutdown();
	}
}
