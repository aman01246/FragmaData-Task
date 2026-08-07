package com.googleOAuth.controller;

import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
	public String profile(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {

		String email = oAuth2User.getAttribute("email");
		Optional<User> user = userService.findByEmail(email);

		if (user.isPresent()) {
			model.addAttribute("user", user.get());
			return "profile";
		}
		
		// User not found, prepare registration page
	    model.addAttribute("name", oAuth2User.getAttribute("name"));
	    model.addAttribute("email", email);
	    model.addAttribute("picture", oAuth2User.getAttribute("picture"));
		
		return "register";
	}

	

	// Save New User
	@PostMapping("/register")
	public String saveUser(@ModelAttribute User user, @AuthenticationPrincipal OAuth2User oauthUser) {

		user.setGoogleId(oauthUser.getAttribute("sub"));
		user.setProfilePicture(oauthUser.getAttribute("picture"));

		userService.save(user);

		return "redirect:/profile";
	}

	

}
