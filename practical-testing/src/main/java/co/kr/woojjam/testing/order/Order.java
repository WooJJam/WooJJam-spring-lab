package co.kr.woojjam.testing.order;

import java.time.LocalDateTime;
import java.util.List;

import co.kr.woojjam.testing.unit.beverage.Beverage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Order {

	private final LocalDateTime orderDateTime;
	private final List<Beverage> beverages;
}
