package com.oauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oauth.entity.User;
import com.oauth.entity.UserIdentity;
import java.util.List;


public interface UserIdentityRepository extends JpaRepository<UserIdentity, Long> {

	Optional<UserIdentity> findByProviderAndProviderUserId(String provider, String providerUserId);

	Optional<UserIdentity> findByProviderAndUser(String provider, User user);
	
	List<UserIdentity> findByUser(User user);
}
