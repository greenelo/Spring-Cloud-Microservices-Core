package com.sssseclipse.web.core.common.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sssseclipse.web.core.common.exception.GenericException;
import com.sssseclipse.web.core.common.exception.details.ExceptionDetailBuilder;
import com.sssseclipse.web.core.common.exception.details.RestResponseExceptionDetail;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler implements ExceptionDetailBuilder{

	@ExceptionHandler(GenericException.class)
	protected ResponseEntity<Object> handleException(GenericException ex, ServletWebRequest request) {
		RestResponseExceptionDetail details = buildRestResponseExceptionDetail(ex, request, ex.getStatus());
		return handleExceptionInternal(details, ex, request);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleException(Exception ex, ServletWebRequest request) {
		RestResponseExceptionDetail details = buildRestResponseExceptionDetail(ex, request, HttpStatus.SERVICE_UNAVAILABLE);
		return handleExceptionInternal(details, ex, request);
	}

	protected ResponseEntity<Object> handleExceptionInternal(RestResponseExceptionDetail details, Exception ex, ServletWebRequest request) {
		log.error(details.toString(), ex);
		
		return handleExceptionInternal(ex, details, new HttpHeaders(), HttpStatus.valueOf(details.getStatus()), request);
	}
}
