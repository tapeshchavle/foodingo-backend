package com.food.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.food.entity.UserEntity;
import com.food.io.UserRequest;
import com.food.io.UserResponse;
import com.food.repository.AuthenticationFacade;
import com.food.repository.UserRepository;
import com.food.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private  AuthenticationFacade authenticationFacade;
	
	@Override
	public UserResponse registerUser(UserRequest request) {
	  UserEntity newUser=userRepository.save(convertToEntity(request));
	  return convertToResponse(newUser);
	}	
	
	private UserEntity convertToEntity(UserRequest request) {
		return UserEntity.builder()
				.email(request.getEmail())
				.name(request.getName())
				.password(passwordEncoder.encode(request.getPassword()))
				.build();		
	}
	private UserResponse convertToResponse(UserEntity entity) {
		return UserResponse.builder()
				.name(entity.getName())
				.email(entity.getEmail())
				.id(entity.getId())
				.build();
	}

	@Override
	public String findByUserId() {
		String loggedInUserEmail=authenticationFacade.getAuthentication().getName();
		UserEntity userEntity= userRepository.findByEmail(loggedInUserEmail).orElseThrow(()-> new UsernameNotFoundException("User email not found"));
		return userEntity.getId();
	}
	
	

}
