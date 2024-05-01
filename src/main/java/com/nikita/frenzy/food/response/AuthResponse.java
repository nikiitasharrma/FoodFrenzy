package com.nikita.frenzy.food.response;

import com.nikita.frenzy.food.models.USER_ROLE;

import lombok.Data;

@Data
public class AuthResponse {
	
	private String jwt;
	
	private String message;
	
	private USER_ROLE role;
}
