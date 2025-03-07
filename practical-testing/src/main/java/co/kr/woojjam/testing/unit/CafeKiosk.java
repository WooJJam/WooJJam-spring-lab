package co.kr.woojjam.testing.unit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import co.kr.woojjam.testing.order.Order;
import co.kr.woojjam.testing.unit.beverage.Beverage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CafeKiosk {

	private final List<Beverage> beverages = new ArrayList<>();

	public void add(Beverage beverage) {
		beverages.add(beverage);
	}

	public void remove(Beverage beverage) {
		beverages.remove(beverage);
	}

	public void clear() {
		beverages.clear();
	}

	public int calculateTotalPrice() {
		int totalPrice = 0;

		for (Beverage beverage : beverages) {
			totalPrice += beverage.getPrice();
		}
		return totalPrice;
	}

	public boolean testMethod() {
		return true;
	}

	public Order createOrder() {
		return new Order(LocalDateTime.now(), beverages);
	}
}
