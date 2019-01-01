package com.sssseclipse.web.core.auth.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import com.sssseclipse.web.core.mongo.entity.AuditableEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Document(collection = "oauth_client")
public class OauthClient extends AuditableEntity implements ClientDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5040301481233628795L;

	@Id
	private String clientId;
	private String clientSecret;
	private Set<String> registeredRedirectUri;
	private Integer accessTokenValiditySeconds;
	private Integer refreshTokenValiditySeconds;
	private Map<String, Object> additionalInformation;
	
	private Set<String> authorizedGrantTypes = new HashSet<>();
	private Set<String> scope = new HashSet<>();
	
	@DBRef(db = "resource")
    private Set<Resource> resource = new HashSet<>();

	@Override
	public Set<String> getResourceIds() {
		return resource.stream().map(Resource::getId).collect(Collectors.toSet());
	}

	@Override
	public boolean isSecretRequired() {
		return !StringUtils.isEmpty(clientSecret);
	}

	@Override
	public boolean isScoped() {
		return !this.scope.isEmpty();
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		// will be loaded by UserDetailsServiceImpl.loadUserByUsername
		return Collections.emptySet();
	}

	@Override
	public boolean isAutoApprove(String scope) {
		return this.scope.contains(scope);
	}

}
