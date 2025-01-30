package co.kr.woojjam.actuator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.kr.woojjam.labsdomain.domains.User;
import co.kr.woojjam.labsdomain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/monitoring")
public class TestController {

	private final UserRepository userRepository;

	@GetMapping("/users")
	public void readUser() {
		User user = userRepository.findById(1L).get();
	}

	@PostMapping("/users")
	public void save() {
		User user = User.builder()
			.name("user name")
			.nickname("user nickname")
			.phoneNumber("user phone")
			.build();

		userRepository.save(user);
	}

}
