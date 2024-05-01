package com.nikita.frenzy.food.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nikita.frenzy.food.models.Restaurant;
import com.nikita.frenzy.food.models.User;
import com.nikita.frenzy.food.request.CreateRestaurantRequest;
import com.nikita.frenzy.food.response.MessageResponse;
import com.nikita.frenzy.food.service.RestaurantService;
import com.nikita.frenzy.food.service.UserService;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

	@Autowired
	private RestaurantService resService;

	@Autowired
	private UserService userService;

	@PostMapping()
	public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception {

		User user = userService.findUserByJwt(jwt);

		Restaurant restaurant = resService.createRestaurant(req, user);

		return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantRequest req, @PathVariable Long id) throws Exception {

		Restaurant restaurant = resService.updateRestaurant(id, req);

		return new ResponseEntity<>(restaurant, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteRestaurant(@PathVariable Long id) throws Exception {

		resService.deleteRestaurant(id);
		MessageResponse res = new MessageResponse();
		res.setMessage("Restaurant deleted successfully");
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<Restaurant> updateRestaurantStatus(@PathVariable Long id) throws Exception {

		Restaurant restaurant = resService.updateRestaurantStatus(id);

		return new ResponseEntity<>(restaurant, HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<Restaurant> findRestaurantByUserId(@RequestHeader("Authorization") String jwt) throws Exception {

		User user = userService.findUserByJwt(jwt);

		Restaurant restaurant = resService.findRestaurantByUserId(user.getId());

		return new ResponseEntity<>(restaurant, HttpStatus.OK);
	}

}
