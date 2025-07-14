package co.kr.woojjam.asynchronous.usecase;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.stereotype.Component;

import co.kr.woojjam.asynchronous.service.UserDashboardCompletableFutureService;
import co.kr.woojjam.asynchronous.service.UserDashboardFutureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDashboardCompletableFutureUseCase {

	private final UserDashboardCompletableFutureService userDashboardCompletableFutureService;

	public void readDashboard() throws ExecutionException, InterruptedException {
		long startTime = System.currentTimeMillis();

		CompletableFuture<String> userFuture = userDashboardCompletableFutureService.readUser();
		CompletableFuture<String> orderFuture = userDashboardCompletableFutureService.readOrders();

		CompletableFuture<Void> completableFuture = userFuture
			.thenCombine(orderFuture, (user, order) ->new String[] {user, order})
			.thenAccept(result -> {
				long endTime = System.currentTimeMillis();
				combine(result[0], result[1], endTime - startTime);
			});

		completableFuture.join(); // 테스트 시 블로킹 (컨트롤러라면 return result 등)
	}

	private void combine(final String user, final String orders, long currentTime) {
		System.out.println("\n📋 사용자 대시보드 정보:");
		System.out.println("User: " + user);
		System.out.println("Recent Orders:\n" + orders);
		System.out.println("\n총 소요 시간: " + currentTime + "ms");
	}
}
