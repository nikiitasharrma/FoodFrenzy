package com.nikita.frenzy.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikita.frenzy.food.config.JwtProvider;
import com.nikita.frenzy.food.models.User;
import com.nikita.frenzy.food.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JwtProvider jwtProvider;

	@Override
	public User findUserByJwt(String jwt) throws Exception {
		
		String email = jwtProvider.getEmailFromJwt(jwt);
		User user = findUserByEmail(email);
		
		return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		
		User user = userRepo.findByEmail(email);
		
		if(user == null) {
			throw new Exception("User does not exist");
		}
		
		return user;
	}
	

}
