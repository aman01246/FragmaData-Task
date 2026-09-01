package com.oauth.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oauth.entity.OAuthProviderConfig;
import com.oauth.service.OAuthProviderService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class OAuthLoginController {

	@Value("${oauth.authorization.base-uri}")
	private String authorizationBaseUri;

	private final OAuthProviderService providerService;

	@GetMapping("/providers")
	public List<Map<String, String>> getEnabledProviders() {

		log.info("Request received to fetch enabled OAuth providers");

		List<OAuthProviderConfig> providers = providerService.getEnabledProviders();

		log.info("Found {} enabled OAuth providers", providers.size());

		return providers.stream().map(provider -> {

			log.debug("Preparing provider response for: {}", provider.getProviderName());

			return Map.of("id", provider.getProviderName(), "name",
					"Continue with " + provider.getProviderName().substring(0, 1).toUpperCase()
							+ provider.getProviderName().substring(1));
		}).toList();
	}

	@GetMapping("/login/{provider}")
	public void login(@PathVariable String provider, HttpServletResponse response) throws IOException {

		log.info("OAuth login request received for provider: {}", provider);

		String providerName = provider.toLowerCase();

		log.debug("Normalized provider name: {}", providerName);

		// Get provider from cache/database
		OAuthProviderConfig config = providerService.getProvider(providerName);

		String redirectUrl = authorizationBaseUri + "/" + config.getProviderName();

		log.info("Redirecting user to Spring Security OAuth endpoint: {}", redirectUrl);

		response.setStatus(HttpStatus.FOUND.value());

		response.sendRedirect(redirectUrl);
	}

}