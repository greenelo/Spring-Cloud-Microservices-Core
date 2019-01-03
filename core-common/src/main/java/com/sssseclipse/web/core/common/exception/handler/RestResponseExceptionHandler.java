package com.sssseclipse.web.core.common.exception.handler;

import java.time.OffsetDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Throwable.class)
	protected ResponseEntity<Object> handleException(RuntimeException ex, ServletWebRequest request) {
		RestResponseExceptionDetails details = RestResponseExceptionDetails.builder()
				.timestamp(OffsetDateTime.now())
				.error(ex.getClass().getSimpleName())
				.message(ex.getMessage())
				.path(request.getRequest().getRequestURI())
				.status(HttpStatus.BAD_REQUEST.value())
				.build();
		return handleExceptionInternal(ex, details, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
}
