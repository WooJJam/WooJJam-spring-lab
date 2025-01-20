package co.kr.woojjam.aop.common;

import java.lang.reflect.Method;
import java.util.Enumeration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

	private final HttpServletRequest request;

	public LoggingAspect(final HttpServletRequest request) {
		this.request = request;
	}

	@Pointcut("execution(* co.kr.woojjam.aop.controller.LoggingController.*(..))")
	public void execute() {
	}

	@Before("execute()")
	public void beforeRequest(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();

		Object[] args = joinPoint.getArgs();

		log.info("\n========================================= Request =========================================");
		log.info("요청 메서드 이름 : {}", method.getName());
		log.info("요청 메서드 경로 : {}", method.getDeclaringClass() + "." + method.getName());
		Enumeration<String> headerNames = request.getHeaderNames();
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				String headerValue = request.getHeader(headerName);
				log.info("요청 헤더 {}  : {}", headerName, headerValue);
			}
		} else {
			log.info("헤더가 존재하지 않습니다.");
		}
		for (Object arg : args) {
			log.info("요청 파라미터 타입 : {} -> 값 {}", arg.getClass().getSimpleName(), arg);
		}
		log.info("===========================================================================================");

	}
}
