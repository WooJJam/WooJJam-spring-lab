package co.kr.woojjam.aop.dto.request;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class TestLoggingReq {

	private String name;
	private String phone;
	private String message;
}
