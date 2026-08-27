package com.oauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "google")
public class OAuthProperties {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
}
