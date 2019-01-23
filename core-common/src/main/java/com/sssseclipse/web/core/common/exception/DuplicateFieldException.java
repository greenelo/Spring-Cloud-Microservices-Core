package com.sssseclipse.web.core.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateFieldException extends GenericException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -843491065867530469L;

	protected HttpStatus status = HttpStatus.BAD_REQUEST;
	private String field;
	
	public DuplicateFieldException() {
        super();
    }
	public DuplicateFieldException(String field) {
        super();
        this.field = field;
    }
    public DuplicateFieldException(String field, String message, Throwable cause) {
        super(message, cause);
        this.field = field;
    }
    public DuplicateFieldException(String field, String message) {
        super(message);
        this.field = field;
    }
    public DuplicateFieldException(String field, Throwable cause) {
        super(cause);
        this.field = field;
    }
    
    @Override
    public String getMessage() {
    	return "Duplicate field: " + field;
    }
}
