package co.kr.woojjam.asynchronous.usecase;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.stereotype.Component;

import co.kr.woojjam.asynchronous.service.UserDashboardFutureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDashboardFutureUseCase {

	private final UserDashboardFutureService userDashboardFutureService;

	public void readDashboard() throws ExecutionException, InterruptedException {
		long startTime = System.currentTimeMillis();
		Future<String> userFuture = userDashboardFutureService.readUser();
		Future<String> orderFuture = userDashboardFutureService.readOrders();

		log.info("userFuture.get 시작");
		String user = userFuture.get();
		log.info("userFuture.get 종료");
		log.info("orderFuture.get 시작");
		String orders = orderFuture.get();
		log.info("orderFuture.get 종료");

		long endTime = System.currentTimeMillis();

		combine(user, orders, endTime - startTime);
	}

	private void combine(final String user, final String orders, long currentTime) {
		System.out.println("\n📋 사용자 대시보드 정보:");
		System.out.println("User: " + user);
		System.out.println("Recent Orders:\n" + orders);
		System.out.println("\n총 소요 시간: " + currentTime + "ms");
	}
}
