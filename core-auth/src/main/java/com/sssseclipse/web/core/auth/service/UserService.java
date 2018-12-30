package com.sssseclipse.web.core.auth.service;

import com.sssseclipse.web.core.auth.entity.User;
import com.sssseclipse.web.core.auth.exception.UserAlreadyExistsException;

public interface UserService {
	
	void create(User user) throws UserAlreadyExistsException;
	
	boolean isUserExisting(String username);
}
