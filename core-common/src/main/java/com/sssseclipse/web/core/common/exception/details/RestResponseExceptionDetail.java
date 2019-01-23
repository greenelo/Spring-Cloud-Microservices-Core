package com.sssseclipse.web.core.common.exception.details;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestResponseExceptionDetail {

	private OffsetDateTime timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;

}
