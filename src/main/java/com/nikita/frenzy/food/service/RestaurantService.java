package com.nikita.frenzy.food.service;

import java.util.List;

import com.nikita.frenzy.food.dto.RestaurantDto;
import com.nikita.frenzy.food.models.Restaurant;
import com.nikita.frenzy.food.models.User;
import com.nikita.frenzy.food.request.CreateRestaurantRequest;

public interface RestaurantService {
	
	Restaurant createRestaurant(CreateRestaurantRequest req, User user);
	
	Restaurant updateRestaurant(Long resId, CreateRestaurantRequest updateReq) throws Exception;
	
	void deleteRestaurant(Long resId) throws Exception;
	
	List<Restaurant> getAllRestaurants();
	
	List<Restaurant> searchRestaurant(String keyword);
	
	Restaurant findRestaurantById(Long resId) throws Exception;
	
	Restaurant findRestaurantByUserId(Long userId) throws Exception;
	
	RestaurantDto addToFavourite(Long resId, User user) throws Exception;
	
	Restaurant updateRestaurantStatus(Long resId) throws Exception;
	
}
