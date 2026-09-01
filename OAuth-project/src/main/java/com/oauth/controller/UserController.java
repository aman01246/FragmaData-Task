package com.oauth.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oauth.dto.ApiResponse;
import com.oauth.dto.CompleteProfileRequest;
import com.oauth.dto.UserIdentityResponse;
import com.oauth.dto.UserProfileResponse;
import com.oauth.entity.User;
import com.oauth.entity.UserIdentity;
import com.oauth.exception.ResourceNotFoundException;
import com.oauth.repository.UserIdentityRepository;
import com.oauth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserRepository userRepository;
	private final UserIdentityRepository userIdentityRepository;

	@PostMapping("/complete-profile")
	public ResponseEntity<ApiResponse<User>> completeProfile(
			@RequestBody CompleteProfileRequest request,
			Authentication authentication) {

		OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

		String email = oauthUser.getAttribute("email");

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		user.setPhone(request.getPhone()); 
		user.setDepartment(request.getDepartment());
		user.setDesignation(request.getDesignation());

		User updatedUser = userRepository.save(user);

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
				HttpStatus.OK.value(),
				"Profile completed successfully",
				updatedUser));
	}

	@GetMapping("/profile")
	public ResponseEntity<ApiResponse<UserProfileResponse>> getMyProfile(Authentication authentication) {

		OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

		String email = oauthUser.getAttribute("email");

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		 // Get all providers connected to this user
	    List<UserIdentity> userIdentities =
	            userIdentityRepository.findByUser(user);

	    // Convert Entity -> DTO
	    List<UserIdentityResponse> identities = userIdentities.stream()
	            .map(identity -> UserIdentityResponse.builder()
	                    .provider(identity.getProvider())
	                    .providerUserId(identity.getProviderUserId())
	                    .build())
	            .toList();

	    UserProfileResponse profile =
	            UserProfileResponse.builder()
	                    .id(user.getId())
	                    .name(user.getName())
	                    .email(user.getEmail())
	                    .profilePicture(user.getProfilePicture())
	                    .phone(user.getPhone())
	                    .department(user.getDepartment())
	                    .designation(user.getDesignation())
	                    .identities(identities)
	                    .build();

		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Profile fetched successfully", profile));
	}
}