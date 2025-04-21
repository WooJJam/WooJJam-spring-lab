package com.dalliza.eventservice.dto;

public record PaymentRequest(
	Long paymentId,
	Long userId
) {}
