package cs.designpattern.observer;

/**
 * 구독자
 */
public class User implements Observer {

	private String name;

	public User(final String name) {
		this.name = name;
	}

	@Override
	public void update(final String news) {
		System.out.println(name + "님에게 뉴스 알림: " + news);
	}
}
