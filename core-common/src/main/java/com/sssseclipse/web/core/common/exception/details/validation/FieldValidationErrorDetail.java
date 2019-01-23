package com.sssseclipse.web.core.common.exception.details.validation;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.sssseclipse.web.core.common.exception.details.RestResponseExceptionDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class FieldValidationErrorDetail extends RestResponseExceptionDetail {

	private List<FieldErrorDetail> fieldErrors = new ArrayList<>();

    public void add(String field, String message) {
    	FieldErrorDetail error = new FieldErrorDetail(field, message);
        fieldErrors.add(error);
    }

    @Builder(builderMethodName = "fieldValidationErrorDetailBuilder")
    public FieldValidationErrorDetail(OffsetDateTime timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }
}
