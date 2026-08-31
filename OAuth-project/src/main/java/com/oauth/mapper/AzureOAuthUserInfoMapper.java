package com.oauth.mapper;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.oauth.dto.OAuthUserInfo;

@Component("azure")
public class AzureOAuthUserInfoMapper implements OAuthUserInfoMapper {

	@Override
	public OAuthUserInfo map(OAuth2User oauthUser) {

		String providerUserId = oauthUser.getAttribute("oid");
		String email = oauthUser.getAttribute("email");
		String name = oauthUser.getAttribute("name");

		// Azure may not always return email. preferred_username is often available.

		if (email == null || email.isBlank()) {

			email = oauthUser.getAttribute("preferred_username");
		}

		if (name == null || name.isBlank()) {

			name = email;
		}

		return OAuthUserInfo.builder()
				.providerUserId(providerUserId)
				.email(email).name(name)
				.picture(null).build();
	}
}