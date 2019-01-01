package com.sssseclipse.web.core.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

import com.sssseclipse.web.core.auth.entity.OauthClient;
import com.sssseclipse.web.core.auth.repository.OauthClientRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value="oauthClientDetailsService")
public class OauthClientDetailsServiceImpl implements ClientDetailsService, ClientRegistrationService{

	@Autowired
	private OauthClientRepository oauthClientRepository;
	
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        log.info("--> OauthClientDetailsServiceImpl.loadClientByClientId clientId={}", clientId);
        
        OauthClient oauthClient = oauthClientRepository.findById(clientId)
        		.orElseThrow(() -> new NoSuchClientException("Client not found"));
        
        log.info("<-- OauthClientDetailsServiceImpl.loadClientByClientId OauthClient={}", oauthClient);
        return oauthClient;
	}

	@Override
	public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
	}

	@Override
	public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
	}

	@Override
	public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
	}

	@Override
	public void removeClientDetails(String clientId) throws NoSuchClientException {
	}

	@Override
	public List<ClientDetails> listClientDetails() {
		return null;
	}

}
