package com.oauth.mapper;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.oauth.dto.OAuthUserInfo;

@Component("aws")
public class AwsOAuthUserInfoMapper implements OAuthUserInfoMapper {

	@Override
	public OAuthUserInfo map(OAuth2User oauthUser) {

		String email = oauthUser.getAttribute("email");

		String name = oauthUser.getAttribute("name");

		if (name == null || name.isBlank()) {
			name = email;
		}
		return OAuthUserInfo.builder()
				.providerUserId(oauthUser.getAttribute("sub"))
				.email(email)
				.name(name)
				.picture(oauthUser.getAttribute("picture"))
				.build();
	}
}
