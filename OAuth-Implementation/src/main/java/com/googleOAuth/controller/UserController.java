package com.googleOAuth.controller;

import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.googleOAuth.entity.User;
import com.googleOAuth.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/profile")
	public String profile(@AuthenticationPrincipal OAuth2User oAuth2User,  OAuth2AuthenticationToken authentication, Model model) {

		String provider = authentication.getAuthorizedClientRegistrationId();
		
		System.out.println("Provider = " + provider);
		System.out.println("OAuth2User = " + oAuth2User);
		System.out.println("Attributes = " + oAuth2User.getAttributes());
		
		//Get Common Information 
		String email = oAuth2User.getAttribute("email");
		
		 if (email == null) {
		        throw new RuntimeException(
		                "Email was not provided by " + provider);
		 }
		
		  // Get provider-specific ID
	        String providerId;
	        String name;
	        String picture;
	        
	        if (provider.equals("google")) {
	        	
	        	 providerId = oAuth2User.getAttribute("sub");
	             name = oAuth2User.getAttribute("name");
	             picture = oAuth2User.getAttribute("picture");
	        }
	        else if (provider.equals("github")) {
	        	
	        	Object githubId = oAuth2User.getAttribute("id");
	        	providerId = String.valueOf(githubId);

	             name = oAuth2User.getAttribute("name");
	             
	             if (name == null) {
	                 name = oAuth2User.getAttribute("login");
	             }
	             
	             picture = oAuth2User.getAttribute("avatar_url");
	        }
	       
	        else {

	            throw new RuntimeException(
	                    "Unsupported provider: " + provider);
	        }
		 
	        // Check existing account / link provider
	        Optional<User> existingUser =
	                userService.processOAuthUser(
	                        provider,
	                        providerId,
	                        email);

	     // Existing user
	        if (existingUser.isPresent()) {

	            model.addAttribute(
	                    "user",
	                    existingUser.get());

	            return "profile";
	        }

		
	     // New user → registration page

	        model.addAttribute("name", name);
	        model.addAttribute("email", email);
	        model.addAttribute("picture", picture);

	        return "register";
	}

	

	// Save New User
	@PostMapping("/register")
	public String saveUser(@ModelAttribute User user, @AuthenticationPrincipal OAuth2User oauthUser, OAuth2AuthenticationToken authentication) {

		 String provider =
		            authentication.getAuthorizedClientRegistrationId();
		
		 String providerId;
	        String picture;
		 
		if (provider.equals("google")) {
			 providerId =
	                    oauthUser.getAttribute("sub");

	            picture =
	                    oauthUser.getAttribute("picture");
		}
		else if(provider.equals("github")) {
	           providerId =
	                    String.valueOf(
	                            oauthUser.getAttribute("id"));

	            picture =
	                    oauthUser.getAttribute("avatar_url");
		}
		else {

            throw new RuntimeException(
                    "Unsupported provider: " + provider);
        }

		 // Set profile picture from OAuth provider
        user.setProfilePicture(picture);


        // Save User
        
                userService.createUserWithProvider(
                        user,
                        provider,
                        providerId);

		return "redirect:/profile";
	}

	

}
