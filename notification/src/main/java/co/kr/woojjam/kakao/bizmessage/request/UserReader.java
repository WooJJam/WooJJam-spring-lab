package co.kr.woojjam.kakao.bizmessage.request;

import org.springframework.stereotype.Component;

import co.kr.woojjam.kakao.bizmessage.repository.UserRepository;
import co.kr.woojjam.labsdomain.domains.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserReader {

	private final UserRepository userRepository;

	public User read(final Long id) {
		return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
	}
}
