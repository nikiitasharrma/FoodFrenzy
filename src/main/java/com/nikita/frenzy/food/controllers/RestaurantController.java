package com.nikita.frenzy.food.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nikita.frenzy.food.dto.RestaurantDto;
import com.nikita.frenzy.food.models.Restaurant;
import com.nikita.frenzy.food.models.User;
import com.nikita.frenzy.food.service.RestaurantService;
import com.nikita.frenzy.food.service.UserService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
	
	@Autowired
	private RestaurantService resService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/search")
	public ResponseEntity<List<Restaurant>> searchRestaurants(@RequestHeader("Authorization") String jwt, @RequestParam String keyword) throws Exception{
		
		List<Restaurant> restaurants = resService.searchRestaurant(keyword);
		
		return new ResponseEntity<>(restaurants, HttpStatus.OK) ;
	}
	
	@GetMapping()
	public ResponseEntity<List<Restaurant>> getAllRestaurants() throws Exception{
		
		List<Restaurant> restaurants = resService.getAllRestaurants();
		
		return new ResponseEntity<>(restaurants, HttpStatus.OK) ;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long id) throws Exception{
		
		Restaurant restaurant = resService.findRestaurantById(id);
		
		return new ResponseEntity<>(restaurant, HttpStatus.OK) ;
	}
	
	@PutMapping("/{id}/add-favourites")
	public ResponseEntity<RestaurantDto> addToFavourites(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{
		
		User user = userService.findUserByJwt(jwt);
		
		RestaurantDto restaurant = resService.addToFavourite(id, user);
		
		return new ResponseEntity<>(restaurant, HttpStatus.OK) ;
	}

}
