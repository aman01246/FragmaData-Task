package com.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OAuthProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuthProjectApplication.class, args);
	}

}
