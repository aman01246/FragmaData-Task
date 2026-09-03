package com.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuthTokenResponse {

    private String provider;

    private String accessToken;
}