package co.kr.woojjam.asynchronous.part2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureCompleteExceptionallyTest {

	@Test
	@DisplayName("CompletableFuture가 예외를 발생시키는 경우")
	void shouldReturnErrorWhenCodeIsNull() {
		// given
		String code = null;

		// when
		CompletableFuture<String> future = findByCode(code);

		// then
		ExecutionException exception = assertThrows(ExecutionException.class, future::get);
		assertTrue(exception.getCause() instanceof IllegalArgumentException);
		assertEquals("code는 null일 수 없습니다.", exception.getCause().getMessage());
	}

	@Test
	@DisplayName("CompletableFuture가 정상적으로 값을 반환하는 경우")
	void shouldReturnValueWhenCodeIsNotNull() throws Exception {
		// given
		String code = "ABC123";

		// when
		CompletableFuture<String> future = findByCode(code);

		// then
		assertEquals("조회된 코드: ABC123", future.get());
	}

	// 실제 서비스 로직
	private CompletableFuture<String> findByCode(String code) {
		if (code == null) {
			CompletableFuture<String> failed = new CompletableFuture<>();
			failed.completeExceptionally(new IllegalArgumentException("code는 null일 수 없습니다."));
			return failed;
		}

		return CompletableFuture.completedFuture("조회된 코드: " + code);
	}
}
