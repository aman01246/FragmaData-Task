package com.oauth.mapper;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.oauth.dto.OAuthUserInfo;

@Component("google")
public class GoogleOAuthUserInfoMapper implements OAuthUserInfoMapper {

	@Override
	public OAuthUserInfo map(OAuth2User oauthUser) {

		return OAuthUserInfo.builder()

				.providerUserId(oauthUser.getAttribute("sub"))

				.email(oauthUser.getAttribute("email"))

				.name(oauthUser.getAttribute("name"))

				.picture(oauthUser.getAttribute("picture"))

				.build();
	}
}
