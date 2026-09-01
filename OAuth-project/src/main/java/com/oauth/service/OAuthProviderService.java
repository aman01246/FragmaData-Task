package com.oauth.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

	// GET ALL ENABLED PROVIDERS
	@Cacheable("enabledProviders")
	public List<OAuthProviderConfig> getEnabledProviders() {

		log.info("CACHE MISS: Loading enabled providers from database");

		return repository.findByEnabledTrue();
	}

	// GET PROVIDER BY NAME
	@Cacheable(value = "oauthProvider", key = "#providerName")
	public OAuthProviderConfig getProvider(String providerName) {

		log.info("CACHE MISS: Loading provider {} from database", providerName);

		return repository.findByProviderNameAndEnabledTrue(providerName)
				.orElseThrow(() -> {
			log.error("OAuth provider not found or disabled: {}", providerName);

			return new RuntimeException("Provider is not available: " + providerName);
		});
	}

	// UPDATE PROVIDER
	@CacheEvict(value = { "enabledProviders", "oauthProvider" }, allEntries = true)
	public OAuthProviderConfig updateProvider(OAuthProviderConfig provider) {

		log.info("DATABASE: Updating provider: " + provider.getProviderName());

		return repository.save(provider);
	}

	// DELETE PROVIDER
	@CacheEvict(value = { "enabledProviders", "oauthProvider" }, allEntries = true)
	public void deleteProvider(String providerName) {

		OAuthProviderConfig provider = repository.findByProviderName(providerName)
				.orElseThrow(() -> new RuntimeException("Provider not found: " + providerName));

		repository.delete(provider);
	}

}