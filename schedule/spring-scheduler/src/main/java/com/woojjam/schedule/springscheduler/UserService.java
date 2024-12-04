package com.woojjam.schedule.springscheduler;

import org.springframework.stereotype.Service;

import com.woojjam.schedule.springscheduler.domain.User;
import com.woojjam.schedule.springscheduler.domain.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public void save(User user) {
        userRepository.save(user);
    }

	public User read(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
	}
}
