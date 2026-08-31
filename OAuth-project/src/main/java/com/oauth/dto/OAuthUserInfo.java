package com.oauth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OAuthUserInfo {

    private String providerUserId;

    private String email;

    private String name;

    private String picture;
}