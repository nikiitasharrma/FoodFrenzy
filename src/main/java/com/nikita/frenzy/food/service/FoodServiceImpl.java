package com.nikita.frenzy.food.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.nikita.frenzy.food.models.Restaurant;
import com.nikita.frenzy.food.models.food.Food;
import com.nikita.frenzy.food.models.food.FoodCategory;
import com.nikita.frenzy.food.repository.FoodRepository;
import com.nikita.frenzy.food.request.CreateFoodRequest;

public class FoodServiceImpl implements FoodService {
	
	@Autowired
	private FoodRepository foodRepo;

	@Override
	public Food createFood(CreateFoodRequest req, FoodCategory category, Restaurant restaurant) {

		Food food = new Food();
		food.setName(req.getName());
		food.setFoodCategory(req.getFoodCategory());
		food.setDescription(req.getDescription());
		food.setIngredients(req.getIngredients());
		food.setPrice(req.getPrice());
		food.setRestaurant(restaurant);
		food.setImages(req.getImages());
		food.setSeasonal(req.isSeasonal());
		food.setVegetarian(req.isVegetarian());
		food.isAvailable();
		food.setCreationDate(new Date());
		
		Food savedFood = foodRepo.save(food);
		restaurant.getFoods().add(savedFood);
		
		return savedFood;
	}

	@Override
	public void deleteFood(Long foodId) throws Exception {
		Food food = findFoodById(foodId);
		food.setRestaurant(null);
		foodRepo.save(food);
	}

	@Override
	public List<Food> getRestaurantsFood(Long resId, boolean isVegetarian, boolean isNonVeg, boolean isSeasonal, String foodCategory) {

		List<Food> foods = foodRepo.findByRestaurantId(resId);
		
		if(isVegetarian) {
			foods = filterByVegetarian(foods,isVegetarian);
		}
		if(isNonVeg) {
			foods = filterByNonVeg(foods,isNonVeg);
		}
		if(isSeasonal) {
			foods = filterBySeasonal(foods,isSeasonal);
		}
		if(foodCategory != null && !foodCategory.equals("")) {
			foods = filterByCategory(foods,foodCategory);
		}
		
		return foods;
	}
	
	private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
		return foods.stream().filter(food -> food.getFoodCategory().getName()  == foodCategory).collect(Collectors.toList());
	}

	private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
		return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());
	}

	private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg) {
		return foods.stream().filter(food -> food.isVegetarian() == false).collect(Collectors.toList());
	}

	private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian){
		return foods.stream().filter(food -> food.isVegetarian() == isVegetarian).collect(Collectors.toList());
	}

	@Override
	public List<Food> searchFood(String keyword) {
		return foodRepo.searchFood(keyword);
	}

	@Override
	public Food findFoodById(Long foodId) throws Exception {
		Food food = foodRepo.findById(foodId).get();
		if(food == null) {
			throw new Exception("Food doesn't exist");
		}
		return food;
	}

	@Override
	public Food updateAvailabilityStatus(Long foodId) throws Exception {
		Food food = findFoodById(foodId);
		food.setAvailable(!food.isAvailable());
		return foodRepo.save(food);
	}

}
