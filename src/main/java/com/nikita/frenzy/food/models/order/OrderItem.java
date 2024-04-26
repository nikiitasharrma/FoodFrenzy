package com.nikita.frenzy.food.models.order;

import java.util.ArrayList;
import java.util.List;

import com.nikita.frenzy.food.models.food.Food;

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
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
    private Food food;
	
    private int quantity;
    
    private Long totalPrice;
    
    private List<String> ingredients = new ArrayList<>();
}
