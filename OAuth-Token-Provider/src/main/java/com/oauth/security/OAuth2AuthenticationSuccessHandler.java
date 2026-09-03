package com.oauth.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.oauth.service.AuthorizationCodeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final OAuth2AuthorizedClientService authorizedClientService;
	private final AuthorizationCodeService authorizationCodeService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication

	) throws IOException {

		OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

		String registrationId = oauthToken.getAuthorizedClientRegistrationId();

		// Get logged-in user
		String principalName = oauthToken.getName();

		OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(registrationId,
				principalName);

		if (authorizedClient == null) {

			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "OAuth authorized client not found");

			return;
		}

		// ACCESS TOKEN
		String accessToken = authorizedClient.getAccessToken().getTokenValue();

		authorizationCodeService.saveToken(registrationId, accessToken);

	}
}