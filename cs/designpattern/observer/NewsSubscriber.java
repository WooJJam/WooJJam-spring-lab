package cs.designpattern.observer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class NewsSubscriber implements PropertyChangeListener {

	private final String name;

	public NewsSubscriber(final String name) {
		this.name = name;
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		System.out.println(name + "님이 새 소식을 받았습니다: " + evt.getNewValue());
	}
}
