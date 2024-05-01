package com.nikita.frenzy.food.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nikita.frenzy.food.config.JwtProvider;
import com.nikita.frenzy.food.models.USER_ROLE;
import com.nikita.frenzy.food.models.User;
import com.nikita.frenzy.food.models.cart.Cart;
import com.nikita.frenzy.food.repository.CartRepository;
import com.nikita.frenzy.food.repository.UserRepository;
import com.nikita.frenzy.food.response.AuthResponse;
import com.nikita.frenzy.food.service.CustomUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private CartRepository cartRepo;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signupHandler(@RequestBody User user) throws Exception{
		
		User existingUser = userRepo.findByEmail(user.getEmail());
		
		if(existingUser != null) {
			throw new Exception("Email already in use with another account. Login now!");
		}
		
		User createdUser = new User();
		createdUser.setEmail(user.getEmail());
		createdUser.setFullName(user.getFullName());
		createdUser.setRole(user.getRole());
		createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User savedUser = userRepo.save(createdUser);
		
		Cart cart = new Cart();
		cart.setCustomer(savedUser);
		cartRepo.save(cart);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtProvider.generateToken(authentication);
		
		AuthResponse authRes = new AuthResponse();
		authRes.setJwt(jwt);
		authRes.setMessage("Register Successful");
		authRes.setRole(savedUser.getRole());
		
		return new ResponseEntity<AuthResponse>(authRes,HttpStatus.CREATED);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginHandler(@RequestBody User user) throws Exception{
		
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
		
		if(userDetails == null) {
			throw new Exception("User with this email id doesn't exist. Signup now!");
		}
		
		if(!passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
			throw new Exception("Incorrect password. Please try again with a correct one!");
		}
		
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
		
		String jwt = jwtProvider.generateToken(authentication);
		
		AuthResponse authRes = new AuthResponse();
		authRes.setJwt(jwt);
		authRes.setMessage("Login Successful");
		authRes.setRole(USER_ROLE.valueOf(authorities.isEmpty()? null : authorities.iterator().next().getAuthority()));
		
		return new ResponseEntity<AuthResponse>(authRes,HttpStatus.OK);
	}

}
