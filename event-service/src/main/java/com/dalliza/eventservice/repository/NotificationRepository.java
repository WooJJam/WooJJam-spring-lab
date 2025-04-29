package com.dalliza.eventservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dalliza.eventservice.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
