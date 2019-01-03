package com.sssseclipse.web.core.auth.aware;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;


@Component
public class UserAuditorAware implements AuditorAware<String> {

	public Optional<String> getCurrentAuditor() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		
		if (authentication.getPrincipal() != null) {
			if (authentication.getPrincipal() instanceof User) {
				return Optional.of(((User) authentication.getPrincipal()).getUsername());
			}else if (authentication.getPrincipal() instanceof String) {
				return Optional.of((String) authentication.getPrincipal());
			}
		}

		return null;
	}

}
