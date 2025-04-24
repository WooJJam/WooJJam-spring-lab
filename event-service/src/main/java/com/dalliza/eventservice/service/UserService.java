package com.dalliza.eventservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dalliza.eventservice.entity.User;
import com.dalliza.eventservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public void save() {
		User user = User.builder()
			.name("WooJJam")
			.phone("010-1234-5678")
			.build();

		userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public User read(final Long id) {
		return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다"));
	}
}
