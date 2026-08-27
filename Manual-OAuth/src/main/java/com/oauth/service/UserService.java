package com.oauth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.oauth.dto.OAuthUserInfo;
import com.oauth.entity.User;
import com.oauth.entity.UserProvider;
import com.oauth.repository.UserProviderRepository;
import com.oauth.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final UserProviderRepository userProviderRepository;

	// =====================================================
	// CHECK EXISTING USER
	// =====================================================

	public User findExistingUser(String provider, String providerId, String email) {

		// 1. Check OAuth provider

		Optional<UserProvider> existingProvider = userProviderRepository.findByProviderAndProviderId(provider,
				providerId);

		if (existingProvider.isPresent()) {
			return existingProvider.get().getUser();
		}

		// 2. Check email

		Optional<User> existingUser = userRepository.findByEmail(email);

		if (existingUser.isPresent()) {

			User user = existingUser.get();

			// Link OAuth provider

			UserProvider userProvider = new UserProvider();
			userProvider.setProvider(provider);
			userProvider.setProviderId(providerId);
			userProvider.setUser(user);
			userProviderRepository.save(userProvider);

			return user;
		}

		// 3. New user

		return null;
	}

	// =====================================================
	// CREATE NEW USER + PROVIDER
	// =====================================================

	@Transactional
	public User createUserWithProvider(User user, String provider, String providerId) {

		// 1. Save User

		User savedUser = userRepository.save(user);

		// 2. Create UserProvider

		UserProvider userProvider = new UserProvider();
		userProvider.setProvider(provider);
		userProvider.setProviderId(providerId);
		userProvider.setUser(savedUser);

		// 3. Save UserProvider

		userProviderRepository.save(userProvider);

		return savedUser;
	}

	@Transactional
	public User registerOAuthUser(User user, OAuthUserInfo userInfo, String provider) {

		user.setName(userInfo.getName());
		user.setEmail(userInfo.getEmail());
		user.setProfilePicture(userInfo.getPicture());

		User savedUser = userRepository.save(user);

		UserProvider userProvider = new UserProvider();

		userProvider.setProvider(provider);
		userProvider.setProviderId(userInfo.getSub());
		userProvider.setUser(savedUser);

		userProviderRepository.save(userProvider);

		return savedUser;
	}

	// =====================================================
	// FIND USER BY ID
	// =====================================================

	public Optional<User> findById(Long id) {

		return userRepository.findById(id);
	}

	// =====================================================
	// FIND USER BY EMAIL
	// =====================================================

	public Optional<User> findByEmail(String email) {

		return userRepository.findByEmail(email);
	}

	// =====================================================
	// SAVE USER
	// =====================================================

	public User save(User user) {

		return userRepository.save(user);
	}
}