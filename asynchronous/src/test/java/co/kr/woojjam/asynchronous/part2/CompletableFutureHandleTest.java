package co.kr.woojjam.asynchronous.part2;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompletableFutureHandleTest {

	@Test
	@DisplayName("handle 정상 처리")
	void handle로_정상_처리() throws ExecutionException, InterruptedException {

		String code = "1234";

		CompletableFuture.supplyAsync(() -> {
			if (code == null) {
				throw new IllegalArgumentException("랜덤 예외 발생");
			}

			return "정상 처리";
		}).handle((result, ex) -> {
			if (ex != null) {
				System.out.println("예외 처리: " + ex.getMessage());
				return "예외 발생 시 기본값 반환";
			}

			return result;
		}).thenAccept((result -> {
			System.out.println("최종 결과: " + result);
		}));
	}

	@Test
	@DisplayName("handle 예외 처리")
	void handle로_예외_처리() throws ExecutionException, InterruptedException {

		String code = "1234";

		CompletableFuture.supplyAsync(() -> {
			if (code != null) {
				throw new IllegalArgumentException("예외 발생");
			}

			return "정상 처리";
		}).handle((result, ex) -> {
			if (ex != null) {
				System.out.println("예외 처리: " + ex.getMessage());
				return "예외 발생 시 기본값 반환";
			}

			return result;
		}).thenAccept((result -> {
			System.out.println("최종 결과: " + result);
		}));
	}
}
