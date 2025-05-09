package com.food.controller;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.ott.GenerateOneTimeTokenFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.io.AuthenticationRequest;
import com.food.io.AuthenticationResponse;
import com.food.service.AppUserDetailsService;
import com.food.util.JwtUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@CrossOrigin(origins = "https://foodingo.netlify.app",
allowCredentials = "true")
public class AuthController{
	private final AuthenticationManager authenticationManager;
	private final AppUserDetailsService userDetailsService;
	private final JwtUtil jwtUtil;
	
	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody AuthenticationRequest request){
	   authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));	
	   UserDetails userDetails= userDetailsService.loadUserByUsername(request.getEmail());
	   final String jwtToken=jwtUtil.generateToken(userDetails);
	   return new AuthenticationResponse(request.getEmail(),jwtToken);
	}
	
}
