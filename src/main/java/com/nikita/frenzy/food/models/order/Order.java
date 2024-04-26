package com.nikita.frenzy.food.models.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nikita.frenzy.food.models.Address;
import com.nikita.frenzy.food.models.Restaurant;
import com.nikita.frenzy.food.models.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private User customer;
    
	@JsonIgnore
	@ManyToOne
	private Restaurant restaurant;
	
    private Long totalAmount;
    
    private String orderStatus;
    
    private Date createdAt;
    
    @ManyToOne
    private Address deliveryAddress;
    
    @OneToMany
    private List<OrderItem> items = new ArrayList<>();
    
//    payment;
    
    private int totalItem;
    
    private int totalPrice;

}
