package com.oauth.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oauth.dto.OAuthUserInfo;
import com.oauth.entity.User;
import com.oauth.entity.UserIdentity;
import com.oauth.exception.BadRequestException;
import com.oauth.repository.UserIdentityRepository;
import com.oauth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final UserIdentityRepository userIdentityRepository;

	@Transactional
	public User processOAuthUser(OAuthUserInfo oauthUserInfo, String provider) {

		String providerUserId = oauthUserInfo.getProviderUserId();
		String email = oauthUserInfo.getEmail();
		String name = oauthUserInfo.getName();
		String picture = oauthUserInfo.getPicture();

		// ==============================
        // BASIC VALIDATION
        // ==============================

        if (providerUserId == null || providerUserId.isBlank()) {
            throw new BadRequestException(
                    "Provider user ID is missing"
            );
        }

        if (email == null || email.isBlank()) {
            throw new BadRequestException(
                    "Email is missing from OAuth provider"
            );
        }

        if (name == null || name.isBlank()) {
            name = email;
        }
		// ==================================
		//  CHECK PROVIDER IDENTITY
		// ==================================

		UserIdentity identity = userIdentityRepository
				.findByProviderAndProviderUserId(provider, providerUserId)
				.orElse(null);

		// USER HAS LOGGED IN WITH THIS PROVIDER BEFORE
		if (identity != null) {

			User user = identity.getUser();
			user.setLastLoginAt(LocalDateTime.now());
			return userRepository.save(user);
		}

		// ==================================
		// 2. CHECK USER BY EMAIL
		// ==================================

		User user = userRepository.findByEmail(email).orElse(null);

		// ==================================
		//  CREATE NEW USER IF NEEDED
		// ==================================

		if (user == null) {

			user = User.builder().name(name).email(email).profilePicture(picture)
					.lastLoginAt(LocalDateTime.now())
					.build();

			user = userRepository.save(user);

		} else {

			// Existing user found by email
			user.setLastLoginAt(LocalDateTime.now());

			user = userRepository.save(user);
		}

		// ==================================
		//  CREATE PROVIDER IDENTITY
		// ==================================

		UserIdentity newIdentity = 
				UserIdentity.builder()
				.user(user).provider(provider)
				.providerUserId(providerUserId)
				.createdAt(LocalDateTime.now()).build();

		userIdentityRepository.save(newIdentity);

		return user;
	}
}