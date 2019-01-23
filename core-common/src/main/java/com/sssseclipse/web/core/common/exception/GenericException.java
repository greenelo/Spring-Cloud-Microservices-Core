package com.sssseclipse.web.core.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6286559441020904170L;

	protected HttpStatus status;
	
	public GenericException() {
        super();
    }

	public GenericException(String message, Throwable cause) {
        super(message, cause);
	}

	public GenericException(String message) {
        super(message);
	}

	public GenericException(Throwable cause) {
        super(cause);
	}
}

