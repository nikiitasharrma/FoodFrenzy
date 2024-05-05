package com.nikita.frenzy.food.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nikita.frenzy.food.models.food.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
	
	List<Food> findByRestaurantId(Long resId);
	
	@Query("SELECT f FROM Food f WHERE lower(f.name) LIKE lower(concat('%',:keyword,'%')) OR lower(f.foodCategory) LIKE lower(concat('%',:keyword,'%'))")
	List<Food> searchFood(@Param("keyword") String keyword);

}
