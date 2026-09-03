package com.oauth.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import com.oauth.entity.OAuthProviderConfig;
import com.oauth.repository.OAuthProviderConfigRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class OAuthClientRegistrationConfig {

	private final OAuthProviderConfigRepository repository;

	@Bean
    ClientRegistrationRepository clientRegistrationRepository() {

		List<OAuthProviderConfig> providers = repository.findByEnabledTrue();

		List<ClientRegistration> registrations = providers.stream().map(this::createRegistration).toList();

		return new InMemoryClientRegistrationRepository(registrations);
	}

	private ClientRegistration createRegistration(OAuthProviderConfig config) {

		return ClientRegistration.withRegistrationId(config.getProviderName())

				.clientId(config.getClientId())

				.clientSecret(config.getClientSecret())

				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)

				.redirectUri(config.getRedirectUri())

				.scope(config.getScopes().split(","))

				.authorizationUri(config.getAuthorizationUri())

				.tokenUri(config.getTokenUri())

				.userInfoUri(config.getUserInfoUri())

				.jwkSetUri(config.getJwkSetUri())

				.userNameAttributeName(config.getUserNameAttributeName())

				.build();
	}
}