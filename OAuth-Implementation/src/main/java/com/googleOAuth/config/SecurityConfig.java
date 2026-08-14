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
			
//			.exceptionHandling(exception -> exception
//			        .authenticationEntryPoint((request, response, authException) -> {
//			            response.sendError(403, "Access Denied");
//			        })
//			    )
			
			.oauth2Login(oauth -> oauth
			        .defaultSuccessUrl("/profile", true)
//			        .failureHandler((request, response, exception) -> {
//
//			            System.out.println("\n==============================");
//			            System.out.println("OAUTH LOGIN FAILED");
//			            System.out.println("Exception class: "
//			                    + exception.getClass().getName());
//			            System.out.println("Message: "
//			                    + exception.getMessage());
//			            System.out.println("Cause: "
//			                    + exception.getCause());
//			            System.out.println("==============================\n");
//
//			            exception.printStackTrace();
//
//			            response.sendRedirect("/login?error");
//			        })
					)
			
			.logout(logout -> logout
					 .logoutUrl("/logout")
					    .logoutSuccessUrl("/")
					    .invalidateHttpSession(true)
					    .clearAuthentication(true)
					    .deleteCookies("JSESSIONID")
					    .permitAll());
		
		return http.build();
		
	}
}

