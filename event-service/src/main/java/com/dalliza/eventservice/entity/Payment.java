package com.dalliza.eventservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

	@Id @GeneratedValue
	private Long id;
	private String name;
	private int amount;
	private int price;

	@Builder
	public Payment(final String name, final int amount, final int price) {
		this.name = name;
		this.amount = amount;
		this.price = price;
	}

	public void decreaseAmount() {
		this.amount -= 1;
	}
}
