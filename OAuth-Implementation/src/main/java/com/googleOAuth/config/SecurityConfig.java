package com.googleOAuth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity  http) throws Exception{
		
		http
			.authorizeHttpRequests(auth -> auth
					 .requestMatchers("/").permitAll()
					.anyRequest().authenticated())
			
			.oauth2Login(oauth -> oauth
			        .defaultSuccessUrl("/profile", true))
			
			.logout(logout -> logout
					.logoutSuccessUrl("/")
					.permitAll());
		
		return http.build();
		
	}
}

