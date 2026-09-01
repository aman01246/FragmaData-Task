package com.oauth.security;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.oauth.dto.OAuthUserInfo;
import com.oauth.entity.User;
import com.oauth.mapper.OAuthUserInfoMapper;
import com.oauth.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final AuthService authService;

	// Map:
	// google -> GoogleOAuthUserInfoMapper
	// aws -> AwsOAuthUserInfoMapper
	// azure -> AzureOAuthUserInfoMapper
	private final Map<String, OAuthUserInfoMapper> mappers;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		log.info("OAuth authentication successful");

		// GET OAUTH USER
		OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

		log.debug("OAuth user principal received");
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

		// google / aws / azure
		String registrationId = token.getAuthorizedClientRegistrationId();

		log.info("OAuth login successful for provider: {}", registrationId);

		// GET CORRECT MAPPER
		OAuthUserInfoMapper mapper = mappers.get(registrationId);

		if (mapper == null) {
			log.error("No OAuth mapper found for provider: {}", registrationId);
			throw new IllegalArgumentException("Unsupported provider: " + registrationId);
		}

		log.debug("OAuth mapper found for provider: {}", registrationId);

		// CONVERT PROVIDER DATA
		OAuthUserInfo userInfo = mapper.map(oauthUser);

		// GET PROVIDER
		String provider = String.valueOf(registrationId.toUpperCase());

		// Find/Create/Update user
		User user = authService.processOAuthUser(userInfo, provider);

		// REDIRECT FRONTEND
		if (user.isProfileCompleted()) {
			log.info("Profile is completed for user ID: {}. Redirecting to profile page", user.getId());

			response.sendRedirect("http://localhost:5501/profile.html");
		} else {
			log.info("Profile is incomplete for user ID: {}. Redirecting to registration page", user.getId());
			response.sendRedirect("http://localhost:5501/register.html");
		}
	}
}