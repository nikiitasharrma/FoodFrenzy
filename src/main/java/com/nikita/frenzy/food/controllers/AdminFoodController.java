package com.nikita.frenzy.food.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nikita.frenzy.food.models.Restaurant;
import com.nikita.frenzy.food.models.food.Food;
import com.nikita.frenzy.food.request.CreateFoodRequest;
import com.nikita.frenzy.food.response.MessageResponse;
import com.nikita.frenzy.food.service.FoodService;
import com.nikita.frenzy.food.service.RestaurantService;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
	
	@Autowired
	private FoodService foodService;
	
	
	@Autowired
	private RestaurantService resService;
	
	@PostMapping
	public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req) throws Exception{
		
		Restaurant restaurant = resService.findRestaurantById(req.getRestaurantId());
		Food food = foodService.createFood(req, req.getFoodCategory(), restaurant);
		
		return new ResponseEntity<Food>(food, HttpStatus.CREATED);
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteFood(@PathVariable("id") Long id) throws Exception{
		
		foodService.deleteFood(id);
		MessageResponse res = new MessageResponse();
		res.setMessage("Food has been delete");
		
		return new ResponseEntity<MessageResponse>(res, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Food> updateFoodAvailability(@PathVariable("id") Long id) throws Exception{
		
		Food food = foodService.updateAvailabilityStatus(id);
		
		return new ResponseEntity<Food>(food, HttpStatus.OK);
	}
	
}
