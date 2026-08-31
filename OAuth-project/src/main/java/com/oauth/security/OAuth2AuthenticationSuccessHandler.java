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

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final AuthService authService;

	// Spring automatically injects all OAuthUserInfoMapper components
	private final Map<String, OAuthUserInfoMapper> mappers;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

		String registrationId = token.getAuthorizedClientRegistrationId();

		// GET CORRECT MAPPER
		OAuthUserInfoMapper mapper = mappers.get(registrationId);

		if (mapper == null) {

			throw new IllegalArgumentException("Unsupported provider: " + registrationId);
		}

		// CONVERT PROVIDER DATA
		OAuthUserInfo userInfo = mapper.map(oauthUser);

		// GET PROVIDER
		String provider = String.valueOf(registrationId.toUpperCase());

		// SAVE / UPDATE USER
		User user = authService.processOAuthUser(userInfo, provider);

		// CONSOLE LOG
		System.out.println("========== OAUTH LOGIN ==========");
		System.out.println("Provider: " + registrationId);
		System.out.println("User Email: " + user.getEmail());
		System.out.println("Profile Completed: " + user.isProfileCompleted());
		System.out.println("=================================");

		// REDIRECT FRONTEND
		if (user.isProfileCompleted()) {
			response.sendRedirect("http://localhost:5501/profile.html");
		} else {
			response.sendRedirect("http://localhost:5501/register.html");
		}
	}
}