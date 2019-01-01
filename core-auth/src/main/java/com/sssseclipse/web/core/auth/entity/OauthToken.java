package com.sssseclipse.web.core.auth.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sssseclipse.web.core.mongo.entity.AuditableEntity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@Document(collection = "oauth_token")
public class OauthToken extends AuditableEntity {

	@Id
	private String id;

	@Indexed(unique = true)
	private String tokenId;

	@Indexed(unique = true)
	private String refreshTokenId;

	@Indexed(unique = true)
	private String authenticationId;

	private String clientId;

	private String grantType;

	private String resourceIds;

	private String scopes;

	private String username;

	private String redirectUri;
	
	private String accessToken;

	private String refreshToken;

	private Integer refreshedCount;
	
	private boolean valid;
	
	private String invalidReason;

	private byte[] accessTokenContent;

	private byte[] authenticationContent;
}
