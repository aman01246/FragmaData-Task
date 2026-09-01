package com.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.oauth.security.OAuth2AuthenticationSuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final OAuth2AuthenticationSuccessHandler successHandler;

	  @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http)
	            throws Exception {

	        http
	        .cors(cors -> {})
	        // Disable CSRF for now
            .csrf(csrf -> csrf.disable())
	        
	            .authorizeHttpRequests(auth -> auth
	            		.requestMatchers(
	            				"/",
	                            "/oauth2/**",
	                            "/login/**",
	                            "/api/auth/providers",
	                            "/api/auth/login/**"
	                    ).permitAll()
	                .anyRequest().authenticated()
	            )

	            .oauth2Login(oauth -> oauth
	                    .successHandler(successHandler)
	                )
	        
	        .logout(logout -> logout
	                .logoutSuccessUrl(
	                    "http://localhost:5501/index.html"
	                )
	                .permitAll()
	            );


	        return http.build();
	    }
}
