package com.sssseclipse.web.core.auth.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sssseclipse.web.core.auth.enumeration.Role;
import com.sssseclipse.web.core.mongo.entity.AuditableEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Document(collection = "user")
public class User extends AuditableEntity implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4916439165909435658L;
	
	@Id
	private String id;

    @Indexed(unique = true)
	private String username;
	
	private String password;
	
	private List<Role> roles = new ArrayList<>();
	
	private boolean expired = false;
	
	private boolean locked = false;
	
	private boolean passwordExpired = false;
	
	private boolean enabled = true;
	
	@Transient
	private Set<GrantedAuthority> authorities = new HashSet<>();
	
	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !this.expired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !this.locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !this.passwordExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (authorities.isEmpty()) {
			authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toSet());
		}
		return authorities;
	}

}
