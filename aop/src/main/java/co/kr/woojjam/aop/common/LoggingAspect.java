package co.kr.woojjam.aop.common;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

	private final HttpServletRequest request;
	private final ObjectMapper objectMapper;

	public LoggingAspect(final HttpServletRequest request, final ObjectMapper objectMapper) {
		this.request = request;
		this.objectMapper = objectMapper;
	}

	@Pointcut("execution(* co.kr.woojjam.aop.controller..*Controller.*(..))")
	public void execute() {
	}

	@Before("execute()")
	public void beforeRequest(JoinPoint joinPoint) throws JsonProcessingException {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();

		Object[] args = joinPoint.getArgs();

		log.info("\n============================================================= Request Start =========================================================");
		log.info("요청 메서드 : {}", method.getDeclaringClass() + "." + method.getName());
		log.info("요청 URL : {}", request.getRequestURL());
		Enumeration<String> headerNames = request.getHeaderNames();
		Map<String, String> headers = new HashMap<>();

		String clientIp = getClientIp(request);
		headers.put("ip", clientIp);

		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			headers.put(headerName, request.getHeader(headerName));
		}
		log.info("요청 헤더 : {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(headers));

		for (Object arg : args) {
			log.info("요청 파라미터 -> (Request : {})", arg);
		}
		log.info("\n============================================================= Request End ===========================================================");
	}

	/**
	* LoggingAspect
	*
	* @author woojjam
	* @date 2025-01-20
	* @description
	* 요청에 대한 응답을 반환합니다.
	**/
	@AfterReturning(pointcut = "execute()", returning = "result")
	public void afterResponse(JoinPoint joinPoint, Object result) throws JsonProcessingException {
		Method method = getMethod(joinPoint);

		log.info("\n============================================================= Response Start ========================================================");

		if (result == null) {
			return;
		}

		log.info("응답 메서드 이름 : {}", method.getDeclaringClass() + "." + method.getName());
		log.info("응답 데이터 : {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
		log.info("\n=========================================================== Response End ===========================================================");
	}

	@AfterThrowing(pointcut = "execute()", throwing = "exception")
	public void afterThrowing(JoinPoint joinPoint, Throwable exception) {
		log.error("\n========================================================== Exception Start =========================================================");
		log.error("예외 메소드: {}", joinPoint.getSignature());
		log.error("예외 종류 : {}, 에러 메시지 : {}", exception.getClass().getSimpleName(), exception.getMessage());
		log.error("\n========================================================== Exception End ===========================================================");
	}


	public String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR");

		//proxy 환경일 경우
		if (ip == null || ip.isEmpty()) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		//웹로직 서버일 경우
		if (ip == null || ip.isEmpty()) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.isEmpty()) {
			ip = request.getRemoteAddr() ;
		}

		return ip;
	}

	private Method getMethod(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		return methodSignature.getMethod();
	}
}
