package com.sssseclipse.web.core.auth.token;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import com.sssseclipse.web.core.auth.entity.OauthToken;
import com.sssseclipse.web.core.auth.repository.OauthTokenRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(value = "oauthTokenStore")
public class OauthTokenStore implements TokenStore {

	@Autowired
	private OauthTokenRepository oauthTokenRepository;
	
	@Autowired
	private OauthTokenStoreSupporter oauthTokenStoreSupporter;
	
	@Autowired
	private AuthenticationIdGenerator authenticationIdGenerator;

	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		return readAuthentication(token.getValue());
	}

	@Override
	public OAuth2Authentication readAuthentication(String tokenValue) {
		log.info("--> OauthTokenStore.readAuthentication tokenValue={}", tokenValue);
		
		String tokenId = oauthTokenStoreSupporter.generateIdFromString(tokenValue);
		OauthToken oauthToken = oauthTokenRepository.findByTokenId(tokenId);

		log.info("--- retrieved oauthToken={} with tokenId={}", oauthToken, tokenId);
		
		OAuth2Authentication authentication = null;
		if(oauthToken != null) {
			authentication = oauthTokenStoreSupporter.deserialize(oauthToken.getAuthenticationContent(), OAuth2Authentication.class);
		}

		log.info("<-- OauthTokenStore.readAuthentication authentication={}", authentication);
		return authentication;
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		log.info("--> OauthTokenStore.storeAccessToken token={}, authentication={}", token, authentication);

		// remove old access token first
		removeAccessToken(token);
		
		// @formatter:off
		OauthToken oauthToken = OauthToken.builder()
				.authenticationId(authenticationIdGenerator.extractId(authentication))
				.tokenId(oauthTokenStoreSupporter.generateIdFromString(token.getValue()))
				.refreshTokenId(token.getRefreshToken() == null ? null : oauthTokenStoreSupporter.generateIdFromString(token.getRefreshToken().getValue()))
				.clientId(authentication.getOAuth2Request().getClientId())
				.grantType(authentication.getOAuth2Request().getGrantType())
				.resourceIds(authentication.getOAuth2Request().getResourceIds().toString())
				.scopes(authentication.getOAuth2Request().getScope().toString())
				.username(authentication.isClientOnly() ? null : authentication.getName())
				.redirectUri(authentication.getOAuth2Request().getRedirectUri())
				.accessToken(token.getValue())
				.refreshToken(token.getRefreshToken() == null ? null : token.getRefreshToken().getValue())
				.refreshedCount(0)
				.valid(true)
				.accessTokenContent(oauthTokenStoreSupporter.serialize(token))
				.authenticationContent(oauthTokenStoreSupporter.serialize(authentication)).build();
		// @formatter:on
		
		oauthTokenRepository.save(oauthToken);

		log.info("<-- OauthTokenStore.storeAccessToken oauthToken={}", oauthToken);
	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		log.info("--> OauthTokenStore.readAccessToken tokenValue={}", tokenValue);

		OauthToken oauthToken = oauthTokenRepository.findByTokenId(oauthTokenStoreSupporter.generateIdFromString(tokenValue));

		OAuth2AccessToken accessToken = null;
		if(oauthToken != null) {
			accessToken = oauthTokenStoreSupporter.deserialize(oauthToken.getAccessTokenContent(), OAuth2AccessToken.class);
		}
		
		log.info("<-- OauthTokenStore.readAccessToken accessToken={}", accessToken);
		return accessToken;
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		log.info("--> OauthTokenStore.removeAccessToken OAuth2AccessToken={}", token);

		String tokenId = oauthTokenStoreSupporter.generateIdFromString(token.getValue());
		OauthToken oauthToken = oauthTokenRepository.findByTokenId(tokenId);

		if (oauthToken != null) {
			oauthTokenRepository.delete(oauthToken);
			log.info("OauthTokenStore.removeAccessToken deleted token, tokenId = {}", tokenId);
		}
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		log.info("--> OauthTokenStore.storeRefreshToken refreshToken={}, authentication={}", refreshToken, authentication);
		// not used since we store refresh token with access token => OauthToken
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		log.info("--> OauthTokenStore.readRefreshToken tokenValue={}", tokenValue);

		OauthToken oauthToken = oauthTokenRepository.findByRefreshTokenId(oauthTokenStoreSupporter.generateIdFromString(tokenValue));

		OAuth2RefreshToken oAuth2RefreshToken = null;
		if (oauthToken != null) {
			oAuth2RefreshToken = new DefaultOAuth2RefreshToken(oauthToken.getRefreshToken());
		}

		log.info("<-- OauthTokenStore.readRefreshToken OAuth2RefreshToken={}", oAuth2RefreshToken);
		return oAuth2RefreshToken;
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		log.info("--> OauthTokenStore.readAuthenticationForRefreshToken token={}", token);

		OauthToken oauthToken = oauthTokenRepository.findByRefreshTokenId(oauthTokenStoreSupporter.generateIdFromString(token.getValue()));
		
		OAuth2Authentication oAuth2Authentication = null;
		if (oauthToken != null) {
			oAuth2Authentication = oauthTokenStoreSupporter.deserialize(oauthToken.getAuthenticationContent(), OAuth2Authentication.class);
		}

		log.info("<-- OauthTokenStore.readAuthenticationForRefreshToken oAuth2Authentication={}", oAuth2Authentication);
		return oAuth2Authentication;
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		log.info("--> OauthTokenStore.removeRefreshToken token={}", token);

		String tokenId = oauthTokenStoreSupporter.generateIdFromString(token.getValue());
		
		OauthToken oauthToken = oauthTokenRepository.findByRefreshTokenId(tokenId);
		if (oauthToken != null) {
			oauthTokenRepository.delete(oauthToken);
			log.info("OauthTokenStore.removeRefreshToken deleted token, refreshTokenId={}", tokenId);
		}
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		log.info("--> OauthTokenStore.removeAccessTokenUsingRefreshToken refreshToken={}", refreshToken);

		removeRefreshToken(refreshToken);
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		log.info("--> OauthTokenStore.getAccessToken authentication={}", authentication);

		String authenticationId = authenticationIdGenerator.extractId(authentication);
		OauthToken oauthToken = oauthTokenRepository.findByAuthenticationId(authenticationId);
		
		OAuth2AccessToken accessToken = null;
		if(oauthToken != null) {
			accessToken = oauthTokenStoreSupporter.deserialize(oauthToken.getAccessTokenContent(), OAuth2AccessToken.class);
		}

		log.info("<-- OauthTokenStore.getAccessToken OAuth2AccessToken={}", accessToken);
		return accessToken;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		log.info("--> OauthTokenStore.findTokensByClientIdAndUserName clientId={}, userName={}", clientId, userName);
		
		Collection<OauthToken> oauthTokens = oauthTokenRepository.findByClientIdAndUsername(clientId, userName);
		
		Collection<OAuth2AccessToken> resultList = oauthTokens.stream()
				.map(oauthToken -> oauthTokenStoreSupporter.deserialize(oauthToken.getAccessTokenContent(), OAuth2AccessToken.class))
				.collect(Collectors.toList());

		log.info("<-- OauthTokenStore.findTokensByClientIdAndUserName Collection<OAuth2AccessToken>={}", resultList);
		return resultList;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		log.info("--> OauthTokenStore.findTokensByClientId clientId={}", clientId);
		Collection<OauthToken> oauthTokens = oauthTokenRepository.findByClientId(clientId);
		
		Collection<OAuth2AccessToken> resultList = oauthTokens.stream()
				.map(oauthToken -> oauthTokenStoreSupporter.deserialize(oauthToken.getAccessTokenContent(), OAuth2AccessToken.class))
				.collect(Collectors.toList());

		log.info("<-- OauthTokenStore.findTokensByClientIdAndUserName Collection<OAuth2AccessToken>={}", resultList);
		return resultList;
	}

}
