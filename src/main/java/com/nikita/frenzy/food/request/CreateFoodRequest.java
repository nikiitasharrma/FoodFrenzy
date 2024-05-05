package com.nikita.frenzy.food.request;

import java.util.List;

import com.nikita.frenzy.food.models.food.FoodCategory;
import com.nikita.frenzy.food.models.ingredients.IngredientsItem;

import lombok.Data;

@Data
public class CreateFoodRequest {
	
	private String name;
	private String description;
	private Long price;
	private FoodCategory foodCategory;
	private List<String> images;
	private Long restaurantId;
	private boolean vegetarian;
	private boolean seasonal;
	private List<IngredientsItem> ingredients;

}
