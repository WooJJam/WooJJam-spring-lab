package co.kr.woojjam.aop.common;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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

	@Pointcut("execution(* co.kr.woojjam.aop.controller.LoggingController.*(..))")
	public void execute() {
	}

	@Before("execute()")
	public void beforeRequest(JoinPoint joinPoint) throws JsonProcessingException {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();

		Object[] args = joinPoint.getArgs();

		log.info("\n========================================= Request =========================================");
		log.info("요청 메서드 이름 : {}", method.getName());
		log.info("요청 메서드 경로 : {}", method.getDeclaringClass() + "." + method.getName());
		Enumeration<String> headerNames = request.getHeaderNames();
		Map<String, String> headers = new HashMap<>();
		String clientIp = getClientIp(request);
		headers.put("ip", clientIp);
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			headers.put(headerName, request.getHeader(headerName));
		}
		ObjectMapper objectMapper = new ObjectMapper();
		log.info("요청 헤더 : {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(headers));

		for (Object arg : args) {
			log.info("요청 파라미터 타입 : {} -> 값 {}", arg.getClass().getSimpleName(), arg);
		}
		log.info("===========================================================================================");

	}

	@AfterReturning(pointcut = "execute()", returning = "result")
	public void afterResponse(JoinPoint joinPoint, Object result) throws JsonProcessingException {
		log.info("\n========================================= Response =========================================");
		log.info("응답 메서드 이름 : {}", joinPoint.getSignature().getName());
		log.info("응답 데이터 : {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
		log.info("===========================================================================================");
	}

	public String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR");

		//proxy 환경일 경우
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		//웹로직 서버일 경우
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0) {
			ip = request.getRemoteAddr() ;
		}

		return ip;
	}
}
