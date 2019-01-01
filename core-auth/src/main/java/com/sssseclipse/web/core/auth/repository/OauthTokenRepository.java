package com.sssseclipse.web.core.auth.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sssseclipse.web.core.auth.entity.OauthToken;

@Repository
public interface OauthTokenRepository extends CrudRepository<OauthToken, String> {
	
	OauthToken findByTokenId(String tokenId);

	OauthToken findByRefreshTokenId(String refreshTokenId);

	OauthToken findByAuthenticationId(String authenticationId);

	Collection<OauthToken> findByClientIdAndUsername(String clientId, String username);
	
	Collection<OauthToken> findByClientId(String clientId);
}