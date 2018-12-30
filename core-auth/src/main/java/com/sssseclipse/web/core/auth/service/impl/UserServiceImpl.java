package com.sssseclipse.web.core.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sssseclipse.web.core.auth.entity.User;
import com.sssseclipse.web.core.auth.exception.UserAlreadyExistsException;
import com.sssseclipse.web.core.auth.repository.UserRepository;
import com.sssseclipse.web.core.auth.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void create(User user) throws UserAlreadyExistsException {
		if (isUserExisting(user.getUsername())) {
			throw new UserAlreadyExistsException(user.getUsername());
		}
		
		String encryptedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);

		userRepository.save(user);

		log.info("Successfully created an user: {}", user.getUsername());
	}

	@Override
	public boolean isUserExisting(String username) {
		return userRepository.existsByUsername(username);
	}

}
