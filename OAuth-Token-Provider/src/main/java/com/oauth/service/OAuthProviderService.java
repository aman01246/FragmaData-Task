package com.oauth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oauth.entity.OAuthProviderConfig;
import com.oauth.repository.OAuthProviderConfigRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthProviderService {

	private final OAuthProviderConfigRepository repository;

	public List<OAuthProviderConfig> getEnabledProviders() {

		log.info("Fetching enabled OAuth providers");

		return repository.findByEnabledTrue();
	}

	public OAuthProviderConfig getProvider(String providerName) {

		log.info("Fetching OAuth provider configuration: {}", providerName);

		return repository.findByProviderNameAndEnabledTrue(providerName).orElseThrow(() -> {

			log.error("OAuth provider not found or disabled: {}", providerName);

			return new RuntimeException("Provider is not available: " + providerName);
		});
	}
}