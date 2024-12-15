package com.product.data.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

	/**
	 * Defining point cut with com.product.data
	 */
	@Pointcut("execution(public * com.product.data..*.*(..))")
	public void publicMethods() {
	}

	/**
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("publicMethods()")
	public Object logMethodEntryAndExit(ProceedingJoinPoint joinPoint) throws Throwable {

		Object[] args = joinPoint.getArgs();

		log.debug("Entering method [{}] with arguments: {}", joinPoint.getSignature(), Arrays.toString(args));

		Object result = joinPoint.proceed();

		log.debug("Exiting method [{}] with result: {}", joinPoint.getSignature(), result);

		return result;
	}

	/**
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("publicMethods()")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();

		Object result = joinPoint.proceed();

		long elapsedTime = System.currentTimeMillis() - startTime;
		log.debug("Method [{}] executed in {} ms", joinPoint.getSignature(), elapsedTime);
		return result;
	}

	/**
	 * Defining point cut with RequestMapping
	 */
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void requestMappingMethods() {
	}
	
	/**
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("requestMappingMethods()")
	public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		log.debug("Request: {} {} - Parameters: {}", request.getMethod(), request.getRequestURI(),
				request.getParameterMap());
		Object result = joinPoint.proceed();
		if (result instanceof ResponseEntity) {
			ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
			log.debug("Response: Status {} - Body: {}", responseEntity.getStatusCode(), responseEntity.getBody());
		}
		return result;
	}

}
