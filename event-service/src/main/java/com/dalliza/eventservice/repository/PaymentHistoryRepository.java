package com.dalliza.eventservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dalliza.eventservice.entity.PaymentHistory;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
}
