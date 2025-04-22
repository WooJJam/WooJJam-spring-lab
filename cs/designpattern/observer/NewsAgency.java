package cs.designpattern.observer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class NewsAgency {
	private final PropertyChangeSupport support = new PropertyChangeSupport(this);
	private String news;

	public void addSubscriber(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	public void removeSubscriber(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}

	public void setNews(String news) {
		String oldNews = this.news;
		this.news = news;
		support.firePropertyChange("news", oldNews, news);
	}
}
