package co.kr.woojjam.aop.common;

import lombok.Getter;

@Getter

public class GlobalException extends RuntimeException {

	private final String message;

	public GlobalException(String message) {
		this.message = message;
	}

	public String getErrorMessage() {
		return message;
	}
}
