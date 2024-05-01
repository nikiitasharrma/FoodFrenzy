package com.nikita.frenzy.food.service;

import com.nikita.frenzy.food.models.User;

public interface UserService {
	
	User findUserByJwt(String jwt) throws Exception;
	
	User findUserByEmail(String email) throws Exception;

}
