package com.googleOAuth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.googleOAuth.entity.User;
import com.googleOAuth.entity.UserProvider;
import com.googleOAuth.repository.UserProviderRepository;
import com.googleOAuth.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final UserProviderRepository userProviderRepository;

	
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	@Transactional
	public Optional<User> processOAuthUser(String provider, String providerId, String email) {

		// Check this OAuth account is already linked
		Optional<UserProvider> existingProvider = userProviderRepository.findByProviderAndProviderId(provider,
				providerId);

		if (existingProvider.isPresent()) {
			return Optional.of(existingProvider.get().getUser());
		}

		//  OAuth account is new.
		// Check whether a User with this email already exists.
		Optional<User> existingUser = userRepository.findByEmail(email);

		if (existingUser.isPresent()) {

			User user = existingUser.get();

			// 3. Link the new OAuth provider to existing User
			UserProvider userProvider = new UserProvider();

			userProvider.setProvider(provider);
			userProvider.setProviderId(providerId);
			userProvider.setUser(user);

			userProviderRepository.save(userProvider);

			return Optional.of(user);
		}

		return Optional.empty();

	}
	
	@Transactional
	public User createUserWithProvider(
	        User user,
	        String provider,
	        String providerId) {

	    User savedUser = userRepository.save(user);

	    UserProvider userProvider = new UserProvider();

	    userProvider.setProvider(provider);
	    userProvider.setProviderId(providerId);
	    userProvider.setUser(savedUser);

	    userProviderRepository.save(userProvider);

	    return savedUser;
	}

}
