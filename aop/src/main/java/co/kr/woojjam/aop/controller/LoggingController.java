package co.kr.woojjam.aop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.kr.woojjam.aop.common.GlobalException;
import co.kr.woojjam.aop.dto.request.TestLoggingReq;
import co.kr.woojjam.labsdomain.domains.User;
import co.kr.woojjam.labsdomain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class LoggingController {

	private final UserRepository userRepository;

	@GetMapping("/logging")
	public void aopTestLogging(@RequestBody TestLoggingReq request) {
		log.info("Test Logging");
		List<User> all = userRepository.findAll();
		throw new IllegalArgumentException("Error 발생 테스트");
	}


	@PostMapping("/logging")
	@Transactional
	public ResponseEntity<User> setAopTestLogging(@RequestBody TestLoggingReq request) {

		User user = User.builder()
			.name(request.getName())
			.phoneNumber(request.getPhone())
			.nickname(request.getMessage())
			.build();

		User result = userRepository.save(user);

		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/errors")
	@Transactional
	public ResponseEntity<User> error(@RequestBody TestLoggingReq request) {

		throw new GlobalException("이건 에러 테스트");
	}

}
