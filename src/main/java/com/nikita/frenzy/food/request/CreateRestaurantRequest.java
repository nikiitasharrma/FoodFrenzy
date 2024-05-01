package com.nikita.frenzy.food.request;

import java.util.List;

import com.nikita.frenzy.food.models.Address;
import com.nikita.frenzy.food.models.ContactInformation;

import lombok.Data;

@Data
public class CreateRestaurantRequest {
	
	private Long id;
	private String name;
	private String cuisineType;
	private String description;
	private Address address;
	private ContactInformation contactInformation;
	private String openingHours;
	private List<String> images;
}
