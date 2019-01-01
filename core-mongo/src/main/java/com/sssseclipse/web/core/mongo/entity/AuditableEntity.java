package com.sssseclipse.web.core.mongo.entity;

import java.time.ZonedDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditableEntity{

	@CreatedDate
	private ZonedDateTime createdDate;

    @CreatedBy
    private String createdBy;
    
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private ZonedDateTime modifiedDate;

    @LastModifiedBy
    private String lastModifiedBy;
    
}
