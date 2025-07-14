package co.kr.woojjam.asynchronous.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.kr.woojjam.asynchronous.usecase.UserDashboardCompletableFutureUseCase;
import co.kr.woojjam.asynchronous.usecase.UserDashboardFutureUseCase;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/completable-future/dashboards")
@RequiredArgsConstructor
public class UserDashboardCompletableFutureController {

	private final UserDashboardCompletableFutureUseCase userDashboardCompletableFutureUseCase;
	@GetMapping
	public ResponseEntity<?> readDashboard() throws ExecutionException, InterruptedException {
		userDashboardCompletableFutureUseCase.readDashboard();
		return ResponseEntity.ok("success");
	}
}
