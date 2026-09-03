package com.oauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oauth.dto.OAuthTokenResponse;
import com.oauth.service.AuthorizationCodeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class OAuthTokenController {

	private final AuthorizationCodeService authorizationCodeService;

	@GetMapping("/token")
	public ResponseEntity<OAuthTokenResponse> getToken() {

		  OAuthTokenResponse tokenData =
	                authorizationCodeService.getTokenData();

	        if (tokenData == null) {
	            return ResponseEntity.notFound().build();
	        }

	        return ResponseEntity.ok(tokenData);
	}
}