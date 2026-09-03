package com.oauth.service;

import org.springframework.stereotype.Service;

import com.oauth.dto.OAuthTokenResponse;

@Service
public class AuthorizationCodeService {

	private OAuthTokenResponse tokenData;

	public void saveToken(String provider, String accessToken) {

		tokenData = new OAuthTokenResponse(provider, accessToken);
	}

	public OAuthTokenResponse getTokenData() {
		return tokenData;
	}
}