package com.sssseclipse.web.core.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sssseclipse.web.core.auth.entity.OauthClient;

@Repository
public interface OauthClientRepository extends CrudRepository<OauthClient, String> {
}