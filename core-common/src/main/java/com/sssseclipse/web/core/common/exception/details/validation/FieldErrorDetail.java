package com.sssseclipse.web.core.common.exception.details.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldErrorDetail {

	private String field;
	private String message;
	
}
