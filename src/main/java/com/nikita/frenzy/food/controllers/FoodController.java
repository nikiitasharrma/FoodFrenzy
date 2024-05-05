package com.nikita.frenzy.food.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nikita.frenzy.food.models.food.Food;
import com.nikita.frenzy.food.service.FoodService;

@RestController
@RequestMapping("/api/food")
public class FoodController {

	@Autowired
	private FoodService foodService;

	@GetMapping("/search")
	public ResponseEntity<List<Food>> createFood(@RequestParam String name) throws Exception {

		List<Food> foods = foodService.searchFood(name);

		return new ResponseEntity<List<Food>>(foods, HttpStatus.OK);
	}

	@GetMapping("/restaurant/resId")
	public ResponseEntity<List<Food>> getRestaurantFood(@PathVariable("resId") Long resId,
			@RequestParam boolean vegetarian, @RequestParam boolean seasonal, @RequestParam boolean nonVeg,
			@RequestParam(required = false) String category) throws Exception {

		List<Food> foods = foodService.getRestaurantsFood(resId, vegetarian, nonVeg, seasonal, category);

		return new ResponseEntity<List<Food>>(foods, HttpStatus.OK);
	}

}
