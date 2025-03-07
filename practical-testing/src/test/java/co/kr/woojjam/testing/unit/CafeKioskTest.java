package co.kr.woojjam.testing.unit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import co.kr.woojjam.testing.unit.beverage.Americano;
import co.kr.woojjam.testing.unit.beverage.Latte;

class CafeKioskTest {

	@Test
	void add() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.add(new Americano());
		System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverages().size());
	}

	@Test
	void removeTest() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		cafeKiosk.add(americano);
		assertThat(cafeKiosk.getBeverages()).hasSize(1);

		cafeKiosk.remove(americano);
		assertThat(cafeKiosk.getBeverages()).isEmpty();
	}

	@Test
	void clearTest() throws Exception {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		Latte latte = new Latte();

		cafeKiosk.add(americano);
		cafeKiosk.add(latte);
		assertThat(cafeKiosk.getBeverages()).hasSize(2);

		cafeKiosk.clear();
		assertThat(cafeKiosk.getBeverages()).isEmpty();
	}

	@Test
	public void 계산_테스트() throws Exception{

		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		Latte latte = new Latte();

		cafeKiosk.add(americano);
		cafeKiosk.add(latte);

		int price = cafeKiosk.calculateTotalPrice();

		assertThat(price).isEqualTo(8500);

	}

	@Test
	public void 삭제할_테스트() throws Exception{
	    CafeKiosk cafeKiosk = new CafeKiosk();
		boolean status = cafeKiosk.testMethod();
		assertThat(status).isEqualTo(true);
	}
}
