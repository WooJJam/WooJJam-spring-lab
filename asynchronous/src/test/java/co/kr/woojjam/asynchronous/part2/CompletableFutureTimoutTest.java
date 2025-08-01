package co.kr.woojjam.asynchronous.part2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompletableFutureTimoutTest {

	@Test
	@DisplayName("get()으로 타임아웃 설정하기")
	void get_타임아웃_설정() throws ExecutionException, InterruptedException {

		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(3000); // 3초 대기
				return "응답 완료";
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

		try {
			String result = future.get(1, TimeUnit.SECONDS); // 1초만 기다림
			System.out.println("결과: " + result);
		} catch (TimeoutException e) {
			System.out.println("타임아웃 발생!");
		}
	}
}
