package co.kr.woojjam.labsdomain.domains;

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
public class User {

	@Id @GeneratedValue
	private Long id;

	private String name;

	@Builder
	public User(final Long id, final String name) {
		this.id = id;
		this.name = name;
	}
}
