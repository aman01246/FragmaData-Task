package com.oauth.dto;

import lombok.Data;

@Data
public class OAuthTokenResponse {

    private String access_token;
    private Integer expires_in;
    private String scope;
    private String token_type;
    private String id_token;
}
