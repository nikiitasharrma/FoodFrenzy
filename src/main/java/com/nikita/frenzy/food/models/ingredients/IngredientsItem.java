package com.nikita.frenzy.food.models.ingredients;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nikita.frenzy.food.models.Restaurant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class IngredientsItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    private String name;
    
    @ManyToOne
    private IngredientCategory category;
    
    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;
    
    private boolean inStock;
}
