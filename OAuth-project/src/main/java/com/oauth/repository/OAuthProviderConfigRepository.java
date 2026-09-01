package com.oauth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oauth.entity.OAuthProviderConfig;

public interface OAuthProviderConfigRepository extends JpaRepository<OAuthProviderConfig, Long> {

	List<OAuthProviderConfig> findByEnabledTrue();

	Optional<OAuthProviderConfig> findByProviderName(String providerName);

	Optional<OAuthProviderConfig> findByProviderNameAndEnabledTrue(String providerName);
}