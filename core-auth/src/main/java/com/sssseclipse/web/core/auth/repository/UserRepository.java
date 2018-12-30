package com.sssseclipse.web.core.auth.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sssseclipse.web.core.auth.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

	Optional<User> findByUsername(String username);
	
	boolean existsByUsername(String username);
}