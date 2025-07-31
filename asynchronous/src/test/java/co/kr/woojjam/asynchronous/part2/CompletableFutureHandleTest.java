package co.kr.woojjam.asynchronous.part2;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompletableFutureHandleTest {

	@Test
	@DisplayName("handle로 예외 처리 및 결과 조작하기")
	void 주문_정보_조회시_예외_처리() throws ExecutionException, InterruptedException {

		CompletableFuture.supplyAsync(() -> {
			if (new Random().nextBoolean()) {
				throw new IllegalArgumentException("랜덤 예외 발생");
			}
			return "정상 결과";
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
