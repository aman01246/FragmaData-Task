package com.oauth.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oauth.dto.OAuthProviderResponse;
import com.oauth.entity.OAuthProviderConfig;
import com.oauth.service.OAuthProviderService;

import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

	private final OAuthProviderService providerService;

	@Value("${oauth.authorization.base-uri}")
	private String authorizationBaseUri;

	// ==========================================
	// GET ENABLED PROVIDERS
	// ==========================================

	@GetMapping("/providers")
	public List<OAuthProviderResponse> getProviders() {


		return providerService
				.getEnabledProviders().stream().map(provider -> OAuthProviderResponse.builder()
						.id(provider.getProviderName()).name(provider.getProviderName().toUpperCase()).build())
				.toList();
	}

	@GetMapping("/login/{provider}")
	public void login(@PathVariable String provider, HttpServletResponse response) throws IOException {

		String providerName = provider.toLowerCase();

		log.info("OAuth login requested for provider: {}", providerName);

		// Validate provider from database
		OAuthProviderConfig config = providerService.getProvider(providerName);

		String authorizationUrl = authorizationBaseUri + "/" + config.getProviderName();


		response.setStatus(HttpStatus.FOUND.value());

		response.sendRedirect(authorizationUrl);
	}
}