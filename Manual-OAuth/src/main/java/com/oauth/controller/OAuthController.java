package com.oauth.controller;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;

import com.oauth.config.OAuthProperties;
import com.oauth.dto.OAuthTokenResponse;
import com.oauth.dto.OAuthUserInfo;
import com.oauth.entity.User;
import com.oauth.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OAuthController {

	private final OAuthProperties oauthProperties;

	private final UserService userService;

	// =====================================================
	// 1. LOGIN WITH GOOGLE
	// =====================================================
	@GetMapping("/oauth/google")
	public void googleLogin(HttpServletResponse response, HttpSession session) throws IOException {

		String state = generateState();

		// state save in session
		session.setAttribute("OAUTH_STATE", state);

		// Build Google Authorization URL
		String authorizationUrl = 
		"https://accounts.google.com/o/oauth2/v2/auth" + 
		"?client_id="+ oauthProperties.getClientId() + 
		"&redirect_uri=" + oauthProperties.getRedirectUri() + 
		"&response_type=code" + 
		"&scope=openid%20profile%20email" + 
		"&state=" + state;

		System.out.println("AuthorizationUrl = " + authorizationUrl);

		// Redirect browser to Google
		response.sendRedirect(authorizationUrl);
	}

	// =====================================================
	// 2. GOOGLE CALLBACK
	// =====================================================

	@GetMapping("/oauth/google/callback")
	public String googleCallback(String code, String state, HttpSession session) {

		// Get state saved before redirecting to Google
		String savedState = (String) session.getAttribute("OAUTH_STATE");
		
		// Validate OAuth state
		if (savedState == null ||
		        !savedState.equals(state)) {

		    throw new RuntimeException(
		            "Invalid OAuth state"
		    );
		}
		
		session.removeAttribute("OAUTH_STATE");

		System.out.println("Authorize code = "+code);
		
		// Authorization Code → Access Token
		OAuthTokenResponse tokenResponse = getAccessToken(code);

		String accessToken = tokenResponse.getAccess_token();

		// Access Token → Google UserInfo
		OAuthUserInfo userInfo = getGoogleUserInfo(accessToken);
		
		  // Check whether user already exists
	    User user = userService.findExistingUser(
	            "google",
	            userInfo.getSub(),
	            userInfo.getEmail()
	    );
	    
	    // Existing user
	    if (user != null) {

	        session.setAttribute( "USER_ID", user.getId()  );

	        return "redirect:/profile";
	    }

	    	// ==========================================
	    // NEW USER
	    // ==========================================

	    // Save Google information temporarily
	    session.setAttribute(
	            "OAUTH_PROVIDER",
	            "google"
	    );

	    session.setAttribute(
	            "OAUTH_USER",
	            userInfo
	    );

	    
	    // Go to registration page
	    return "redirect:/register";
	}
	  
	
	private String generateState() {

	    byte[] randomBytes = new byte[32];

	    SecureRandom secureRandom = new SecureRandom();

	    secureRandom.nextBytes(randomBytes);

	    return Base64.getUrlEncoder()
	            .withoutPadding()
	            .encodeToString(randomBytes);
	}

	private OAuthTokenResponse getAccessToken(String code) {
		RestClient restClient = RestClient.create();

		return restClient.post().uri("https://oauth2.googleapis.com/token")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.body("client_id=" + oauthProperties.getClientId() + 
						"&client_secret="+ oauthProperties.getClientSecret() + 
						"&code=" + code + 
						"&redirect_uri="+ oauthProperties.getRedirectUri() + 
						"&grant_type=authorization_code")
				.retrieve().body(OAuthTokenResponse.class);
	}

	private OAuthUserInfo getGoogleUserInfo(String accessToken) {
		RestClient restClient = RestClient.create();
		return restClient.get()
				.uri("https://openidconnect.googleapis.com/v1/userinfo")
				.header("Authorization", "Bearer " + accessToken)
				.retrieve().body(OAuthUserInfo.class);
	}
	

}
