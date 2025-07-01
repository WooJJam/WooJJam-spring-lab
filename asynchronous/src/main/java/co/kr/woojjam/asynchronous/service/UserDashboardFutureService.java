package co.kr.woojjam.asynchronous.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDashboardFutureService {

	private final ExecutorService executorService = Executors.newFixedThreadPool(5);

	public Future<String> readUser() {
		return executorService.submit(() -> {
			Thread.sleep(2000);
			return "WooJJam(25세)";
		});
	}

	public Future<String> readOrders() {
		return executorService.submit(() -> {
			Thread.sleep(2000);
			return "- Book\n- Pen";
		});
	}
}
