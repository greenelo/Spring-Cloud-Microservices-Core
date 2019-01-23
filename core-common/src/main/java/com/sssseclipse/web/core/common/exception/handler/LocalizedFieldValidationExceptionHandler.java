package com.sssseclipse.web.core.common.exception.handler;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.sssseclipse.web.core.common.exception.details.ExceptionDetailBuilder;
import com.sssseclipse.web.core.common.exception.details.validation.FieldValidationErrorDetail;

@RestControllerAdvice
public class LocalizedFieldValidationExceptionHandler extends RestResponseExceptionHandler implements ExceptionDetailBuilder{

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		
		FieldValidationErrorDetail detail = buildFieldValidationErrorDetail(ex, (ServletWebRequest) request, HttpStatus.BAD_REQUEST);
		processFieldErrors(detail, fieldErrors);
		return handleExceptionInternal(detail, ex, (ServletWebRequest) request);
	}


	private void processFieldErrors(FieldValidationErrorDetail detail, List<FieldError> fieldErrors) {
		for (FieldError fieldError : fieldErrors) {
			String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
			detail.add(fieldError.getField(), localizedErrorMessage);
		}
	}

	private String resolveLocalizedErrorMessage(FieldError fieldError) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

		return localizedErrorMessage;
	}
}
