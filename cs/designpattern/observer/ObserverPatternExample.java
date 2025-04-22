package cs.designpattern.observer;

public class ObserverPatternExample {
	public static void main(String[] args) {
		runCustomObserver();

		runPropertyChange();
	}

	private static void runCustomObserver() {
		NewsPublisher publisher = new NewsPublisher();

		publisher.registerObserver(new User("재민"));
		publisher.registerObserver(new User("우쨈"));

		publisher.publishNews("옵저버 패턴 정식 출시!");
	}

	private static void runPropertyChange() {
		NewsAgency agency = new NewsAgency();

		agency.addSubscriber(new NewsSubscriber("재민"));
		agency.addSubscriber(new NewsSubscriber("우쨈"));

		agency.setNews("옵저버 패턴이 업데이트 되었습니다!");
	}
}
