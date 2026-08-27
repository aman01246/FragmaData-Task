package com.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.oauth.dto.OAuthUserInfo;
import com.oauth.entity.User;
import com.oauth.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final UserService userService;

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/profile")
	public String profile(HttpSession session, Model model) {

		Long userId = (Long) session.getAttribute("USER_ID");

		if (userId == null) {
			return "redirect:/";
		}

		User user = userService.findById(userId).orElseThrow();

		model.addAttribute("user", user);

		return "profile";
	}

	@GetMapping("/register")
	public String registerPage(HttpSession session, Model model) {

		OAuthUserInfo userInfo = (OAuthUserInfo) session.getAttribute("OAUTH_USER");

		if (userInfo == null) {
			return "redirect:/";
		}

		model.addAttribute("userInfo", userInfo);

		return "register";
	}

	@PostMapping("/register")
	public String saveUser(@ModelAttribute User user, HttpSession session) {

		OAuthUserInfo userInfo = (OAuthUserInfo) session.getAttribute("OAUTH_USER");

		String provider = (String) session.getAttribute("OAUTH_PROVIDER");

		if (userInfo == null || provider == null) {
			return "redirect:/";
		}

		User savedUser = userService.registerOAuthUser(user, userInfo, provider);

		session.setAttribute("USER_ID", savedUser.getId());
		
		session.removeAttribute("OAUTH_USER");
		session.removeAttribute("OAUTH_PROVIDER");

		return "redirect:/profile";
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {

		session.invalidate();
		System.out.println("Logout Success");
		return "redirect:/";
	}
}
