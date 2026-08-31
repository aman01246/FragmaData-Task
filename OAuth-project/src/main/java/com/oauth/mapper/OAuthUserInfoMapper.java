package com.oauth.mapper;


import org.springframework.security.oauth2.core.user.OAuth2User;

import com.oauth.dto.OAuthUserInfo;

public interface OAuthUserInfoMapper {

	 OAuthUserInfo map(OAuth2User oauthUser);
}
