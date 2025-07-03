package co.kr.woojjam.asynchronous.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

@Service
public class UserDashboardCompletableFutureService {

	private final ExecutorService executorService = Executors.newCachedThreadPool();

	public CompletableFuture<String> readUser() {
		return CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			}
			return "WooJJam(25세)";
		}, executorService);
	}

	public CompletableFuture<String> readOrders() {
		return CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			}
			return "- Book\n- Pen";
		}, executorService);
	}
}
