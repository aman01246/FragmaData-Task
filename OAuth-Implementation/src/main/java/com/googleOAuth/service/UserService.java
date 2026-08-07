package com.googleOAuth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.googleOAuth.entity.User;
import com.googleOAuth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository repo;
	
	    public Optional<User> findByEmail(String email) {
	        return repo.findByEmail(email);
	    }

	    public User save(User user) {
	        return repo.save(user);
	    }

		

}
