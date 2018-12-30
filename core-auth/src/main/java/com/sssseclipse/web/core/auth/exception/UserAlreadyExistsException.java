package com.sssseclipse.web.core.auth.exception;

public class UserAlreadyExistsException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 6524829144594416107L;

	public UserAlreadyExistsException(String username) {
        super("Username: " + username + " alreay exists");
    }

}
