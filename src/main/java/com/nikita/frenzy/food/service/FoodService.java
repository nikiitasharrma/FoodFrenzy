package com.nikita.frenzy.food.service;

import java.util.List;

import com.nikita.frenzy.food.models.Restaurant;
import com.nikita.frenzy.food.models.food.Food;
import com.nikita.frenzy.food.models.food.FoodCategory;
import com.nikita.frenzy.food.request.CreateFoodRequest;

public interface FoodService {
	
	Food createFood(CreateFoodRequest req, FoodCategory category, Restaurant restaurant);
	
	void deleteFood(Long foodId) throws Exception;
	
	List<Food> getRestaurantsFood(Long resId, boolean isVegetarian, boolean isNonVeg, boolean isSeasonal, String foodCategory);
	
	List<Food> searchFood(String keyword);
	
	Food findFoodById(Long foodId) throws Exception;
	
	Food updateAvailabilityStatus(Long foodId) throws Exception;

}
