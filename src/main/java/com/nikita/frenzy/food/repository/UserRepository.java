package com.nikita.frenzy.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikita.frenzy.food.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByEmail(String username);

}
