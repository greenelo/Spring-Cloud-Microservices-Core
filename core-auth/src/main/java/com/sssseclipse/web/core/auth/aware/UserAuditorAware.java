package com.sssseclipse.web.core.auth.aware;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sssseclipse.web.core.auth.entity.User;

@Component
public class UserAuditorAware implements AuditorAware<String> {

	public Optional<String> getCurrentAuditor() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}

		return Optional.of(((User) authentication.getPrincipal()).getUsername());
	}

}
