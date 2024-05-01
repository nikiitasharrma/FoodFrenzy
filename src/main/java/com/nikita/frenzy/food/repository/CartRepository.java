package com.nikita.frenzy.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikita.frenzy.food.models.cart.Cart;

public interface CartRepository extends JpaRepository<Cart,Long> {

}
