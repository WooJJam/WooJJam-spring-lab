package com.dalliza.eventservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dalliza.eventservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
