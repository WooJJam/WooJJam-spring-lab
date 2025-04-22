package cs.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

public class NewsPublisher implements Subject {

	private List<Observer> observers = new ArrayList<>();

	@Override
	public void registerObserver(final Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(final Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(final String news) {
		for (Observer o : observers) {
			o.update(news);
		}
	}

	public void publishNews(String news) {
		System.out.println("뉴스 발행됨: " + news);
		notifyObservers(news);
	}
}
