package com.sssseclipse.web.core.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sssseclipse.web.core.auth.entity.User;
import com.sssseclipse.web.core.auth.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value="userDetailsSerivce")
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("--> UserDetailsServiceImpl.loadUserByUsername username={}", username);
        
		User user = userRepository.findByUsername(username)
						.orElseThrow(()->new BadCredentialsException("Invalid username or password"));
		
		if (user.getRoles().isEmpty()) {
			throw new InsufficientAuthenticationException("Insufficient authentication");
		}

        log.info("<-- UserDetailsServiceImpl.loadUserByUsername UserDetails={}", user);
		return user;
	}

}
