package com.sssseclipse.web.core.common.exception.handler;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestResponseExceptionDetails {

	private OffsetDateTime timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;
}
