package com.dalliza.eventservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dalliza.eventservice.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
