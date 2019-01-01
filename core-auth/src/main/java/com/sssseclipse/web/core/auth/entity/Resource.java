package com.sssseclipse.web.core.auth.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "resource")
public class Resource {

	@Id
	private String id;
	
	private String label;
}
