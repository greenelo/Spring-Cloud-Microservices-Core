package com.sssseclipse.web.core.common.exception.details;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;

import com.sssseclipse.web.core.common.exception.details.validation.FieldValidationErrorDetail;

public interface ExceptionDetailBuilder {

	default RestResponseExceptionDetail buildRestResponseExceptionDetail(Exception ex, ServletWebRequest request, HttpStatus status) {
		// @formatter:off
		return RestResponseExceptionDetail.builder()
				.timestamp(OffsetDateTime.now())
				.error(ex.getClass().getSimpleName())
				.message(ex.getLocalizedMessage())
				.status(status.value())
				.path(request.getRequest().getRequestURI())
				.build();
		// @formatter:on
	}
	
	default FieldValidationErrorDetail buildFieldValidationErrorDetail(Exception ex, ServletWebRequest request, HttpStatus status) {
		// @formatter:off
		return FieldValidationErrorDetail.fieldValidationErrorDetailBuilder()
				.timestamp(OffsetDateTime.now())
				.error(ex.getClass().getSimpleName())
//				no need to show message
//				.message(ex.getLocalizedMessage())
				.status(status.value())
				.path(request.getRequest().getRequestURI())
				.build();
		// @formatter:on
	}
}
