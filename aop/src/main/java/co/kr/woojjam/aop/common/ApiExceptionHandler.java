package co.kr.woojjam.aop.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<?> handleGlobalException(GlobalException globalException) {
		log.warn("[Exception] {}", globalException.getErrorMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(globalException.getErrorMessage());
	}
}
