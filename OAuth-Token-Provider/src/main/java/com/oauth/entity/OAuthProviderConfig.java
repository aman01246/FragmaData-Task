package com.oauth.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "oauth_provider_configs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OAuthProviderConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // google / aws / azure
    @Column(nullable = false, unique = true)
    private String providerName;


    @Column(nullable = false)
    private String clientId;

    @Column(nullable = false, length = 2000)
    private String clientSecret;

    @Column(nullable = false)
    private String redirectUri;

    // OAuth endpoints
    private String authorizationUri;

    private String tokenUri;

    private String userInfoUri;

    private String jwkSetUri;

    private String userNameAttributeName;

    // openid,profile,email
    private String scopes;

    private boolean enabled;
}