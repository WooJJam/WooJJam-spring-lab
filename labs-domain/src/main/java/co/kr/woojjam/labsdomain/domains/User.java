package co.kr.woojjam.labsdomain.domains;

import co.kr.woojjam.labsdomain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@ToString(of = {"id", "name"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	@Id @GeneratedValue
	private Long id;
	private String phoneNumber;
	private String name;
	private String nickname;


	@Builder
	public User(final Long id, final String name, String nickname, String phoneNumber) {
		this.id = id;
		this.name = name;
		this.nickname = nickname;
		this.phoneNumber = phoneNumber;
	}

}
