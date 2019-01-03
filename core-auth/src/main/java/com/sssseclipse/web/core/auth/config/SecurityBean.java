package com.sssseclipse.web.core.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.sssseclipse.web.core.auth.token.OauthTokenStore;

@Configuration
@ComponentScan("com.sssseclipse.web")
public class SecurityBean {

	@Autowired
	private Environment env;
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(env.getProperty("JWT_KEY_VALUE"));
        return converter;
	}
	
    @Bean
    public TokenStore tokenStore() {
        return new OauthTokenStore();
    }
    
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
