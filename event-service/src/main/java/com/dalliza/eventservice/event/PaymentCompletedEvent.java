package com.dalliza.eventservice.event;

import com.dalliza.eventservice.entity.Payment;
import com.dalliza.eventservice.entity.User;

public record PaymentCompletedEvent(Payment payment, User user) {
}
