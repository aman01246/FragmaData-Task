package com.oauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oauth.entity.UserProvider;

public interface UserProviderRepository extends JpaRepository<UserProvider, Long> {

	Optional<UserProvider> findByProviderAndProviderId(String provider, String providerId);

}
